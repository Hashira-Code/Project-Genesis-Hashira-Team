package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.Team
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import domain.repository.TeamRepo
import domain.validation.Validator
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GenerateCrossTeamPerformanceReportUseCase(
    private val teamRepo: TeamRepo,
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo,
    private val scoreValidator: Validator<Double, Double>
) {
    suspend operator fun invoke(): Result<List<Pair<String, Double>>> = coroutineScope {
        val teamsDeferred = async { teamRepo.getAll() }
        val menteesDeferred = async { menteeRepo.getAll() }
        val submissionsDeferred = async { performanceRepo.getAll() }

        val teams = teamsDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val mentees = menteesDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val submissions = submissionsDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }

        val menteeIdsByTeam = groupMenteeIdsByTeam(mentees)
        val submissionsByMenteeId = groupSubmissionsByMentee(submissions)

        Result.success(buildReport(teams, menteeIdsByTeam, submissionsByMenteeId))
    }

    private fun buildReport(
        teams: List<Team>,
        menteeIdsByTeam: Map<String, List<String>>,
        submissionsByMenteeId: Map<String, List<PerformanceSubmission>>
    ): List<Pair<String, Double>> {
        return teams
            .map { team ->
                team.name to calculateTeamAverage(
                    menteeIdsByTeam[team.id].orEmpty(),
                    submissionsByMenteeId
                )
            }
            .sortedWith(compareByDescending<Pair<String, Double>> { it.second }.thenBy { it.first })
    }

    private fun groupMenteeIdsByTeam(mentees: List<Mentee>): Map<String, List<String>> {
        return mentees.groupBy(
            keySelector = { it.teamId },
            valueTransform = { it.id }
        )
    }

    private fun groupSubmissionsByMentee(
        submissions: List<PerformanceSubmission>
    ): Map<String, List<PerformanceSubmission>> {
        return submissions.groupBy { it.menteeId }
    }

    private fun calculateTeamAverage(
        menteeIds: List<String>,
        submissionsByMenteeId: Map<String, List<PerformanceSubmission>>
    ): Double {
        val validScores = menteeIds.flatMap { menteeId ->
            submissionsByMenteeId[menteeId].orEmpty()
                .mapNotNull { validScoreOrNull(it) }
        }

        return if (validScores.isEmpty()) 0.0 else validScores.average()
    }

    private fun validScoreOrNull(submission: PerformanceSubmission): Double? {
        return scoreValidator.validate(submission.score).getOrNull()
    }
}
