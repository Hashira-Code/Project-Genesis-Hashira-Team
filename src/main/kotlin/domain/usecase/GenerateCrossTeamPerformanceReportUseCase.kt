package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.Team
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import domain.repository.TeamRepo

class GenerateCrossTeamPerformanceReportUseCase(
    private val teamRepo: TeamRepo,
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
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
        val menteeIdsByTeam = mentees.groupBy(
            keySelector = { it.teamId },
            valueTransform = { it.id }
        )
        val submissionsByMenteeId = submissions.groupBy { it.menteeId }

        return teams
            .map { team ->
                team.name to calculateTeamAverage(
                    menteeIdsByTeam[team.id].orEmpty(),
                    submissionsByMenteeId
                )
            }
            .sortedByDescending { it.second }
    }

    private fun calculateTeamAverage(
        menteeIds: List<String>,
        submissionsByMenteeId: Map<String, List<PerformanceSubmission>>
    ): Double {
        val scores = menteeIds.flatMap { menteeId ->
            submissionsByMenteeId[menteeId].orEmpty().map { it.score }
        }

        return scores.average()
    }
}
