package domain.useCase
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetOverallPerformanceAverageForTeamUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    fun execute(teamId: String): Double {
        val menteeIds = menteeRepo
            .getByTeamId(teamId)
            .map { it.id }

        if (menteeIds.isEmpty()) return 0.0
        val scores = performanceRepo
            .getAll()
            .filter { it.menteeId in menteeIds }
            .map { it.score }

        return if (scores.isEmpty()) 0.0 else scores.average()
    }
}