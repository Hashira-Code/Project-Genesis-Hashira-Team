package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.Team
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import domain.repository.TeamRepo
import domain.validation.Validator

class GenerateCrossTeamPerformanceReportUseCase(
    private val teamRepo: TeamRepo,
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo,
    private val scoreValidator: Validator<Double, Double>
) {
    operator fun invoke(): Result<List<Pair<String, Double>>> {
        val teams = teamRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val mentees = menteeRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val submissions = performanceRepo.getAll().getOrElse {
            return Result.failure(it)
        }

        return Result.success(buildReport(teams, mentees, submissions))
    }

    private fun buildReport(
        teams: List<Team>,
        mentees: List<Mentee>,
        submissions: List<PerformanceSubmission>
    ): List<Pair<String, Double>> {
        val menteeIdsByTeam = groupMenteeIdsByTeam(mentees)
        val submissionsByMenteeId = groupSubmissionsByMentee(submissions)

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
