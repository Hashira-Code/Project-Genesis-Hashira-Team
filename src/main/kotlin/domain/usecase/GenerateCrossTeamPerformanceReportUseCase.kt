package domain.usecase

import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import domain.repository.TeamRepo

class GenerateCrossTeamPerformanceReportUseCase(
    private val teamRepo: TeamRepo,
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {
    operator fun invoke(): Result<List<Pair<String, Double>>> {
        TODO("Implement cross-team performance report")
    }
}
