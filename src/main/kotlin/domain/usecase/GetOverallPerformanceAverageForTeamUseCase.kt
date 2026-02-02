package domain.usecase
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetOverallPerformanceAverageForTeamUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    fun execute(teamId: String): Double {
        return calculateAverage(
            getScoresForMentees(
                getMenteeIdsByTeam(teamId)
            )
        )
    }

    private fun getMenteeIdsByTeam(teamId: String): List<String> {
        return menteeRepo
            .getByTeamId(teamId)
            .map { it.id }
    }

    private fun getScoresForMentees(menteeIds: List<String>): List<Double> {
        return performanceRepo
            .getAll()
            .filter { it.menteeId in menteeIds }
            .map { it.score }
    }

    private fun calculateAverage(scores: List<Double>): Double {
        return scores.takeIf { it.isNotEmpty() }?.average() ?: 0.0
    }
}
