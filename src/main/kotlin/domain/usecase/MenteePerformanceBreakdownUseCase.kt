package domain.usecase
import domain.model.SubmissionType
import domain.repository.PerformanceRepo

class GetPerformanceBreakdownForMenteeUseCase(
    private val performanceRepo: PerformanceRepo
) {
    fun execute(menteeId: String): Map<SubmissionType, Double> {
        return performanceRepo.getByMenteeId(menteeId)
            .groupBy { it.type }
            .mapValues { (_, submissions) ->
                submissions.map { it.score }.average()
            }
    }
}