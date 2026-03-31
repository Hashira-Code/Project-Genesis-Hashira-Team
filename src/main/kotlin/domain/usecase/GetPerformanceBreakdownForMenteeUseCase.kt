package domain.usecase

import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType
import domain.repository.PerformanceRepo
import domain.model.request.MenteeIdRequest
import domain.model.exception.ValidationExeption.DataNotFoundExeption

class GetPerformanceBreakdownForMenteeUseCase(
    private val performanceRepo: PerformanceRepo,
) {
    operator fun invoke(request: MenteeIdRequest): Result<Map<SubmissionType, Double>> {
        val submissions = performanceRepo.getByMenteeId(request.id).getOrElse {
            return Result.failure(it)
        }
        if (submissions.isEmpty()) {
            return Result.failure(DataNotFoundExeption(NO_DATA_MSG))
        }
        val breakdown = calculateBreakdown(submissions)

        return Result.success(breakdown)
    }

    private fun calculateBreakdown(submissions: List<PerformanceSubmission>): Map<SubmissionType, Double> {
        return submissions.groupBy { it.type }.mapValues { entry -> entry.value.map { it.score }.average() }
    }

    companion object {
        const val NO_DATA_MSG = "No performance data found for this mentee"
    }

}

