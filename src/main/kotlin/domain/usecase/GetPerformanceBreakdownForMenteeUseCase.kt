package domain.usecase

import domain.model.entity.SubmissionType
import domain.repository.PerformanceRepo
import domain.model.request.MenteeIdRequest
import domain.validation.Validator
import domain.model.exception.ValidationException.DataNotFoundException

class GetPerformanceBreakdownForMenteeUseCase(
    private val performanceRepo: PerformanceRepo,
    private val menteeIdValidator: Validator<String, String>
) {

    operator fun invoke(request: MenteeIdRequest): Result<Map<SubmissionType, Double>> {
        val menteeId = menteeIdValidator.validate(request.id).getOrElse {
            return Result.failure(it)
        }
        val submissions = performanceRepo.getByMenteeId(menteeId).getOrElse {
            return Result.failure(it)
        }
        if (submissions.isEmpty()) {
            return Result.failure(DataNotFoundException(NO_DATA_MSG))
        }
        val breakdown = submissions
            .groupBy { it.type }
            .mapValues { entry ->
                entry.value.map { it.score }.average()
            }

        return Result.success(breakdown)
    }

    companion object {
        const val NO_DATA_MSG = "No performance data found for this mentee"
    }

}

