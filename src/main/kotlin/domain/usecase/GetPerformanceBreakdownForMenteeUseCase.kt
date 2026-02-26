package domain.usecase

import domain.model.SubmissionType
import domain.repository.PerformanceRepo
import domain.request.MenteeIdRequest
import domain.validation.Validator

class GetPerformanceBreakdownForMenteeUseCase(
    private val performanceRepo: PerformanceRepo,
    private val menteeIdValidator: Validator<String, String>
) {

    operator fun invoke(request: MenteeIdRequest): Result<Map<SubmissionType, Double>> {
        return menteeIdValidator.validate(request.id).fold(
            onSuccess = { menteeId ->
                val submissions = performanceRepo
                    .getByMenteeId(menteeId)
                    .asSequence()

                if (submissions.none()) {
                    Result.failure(Exception("No performance data found for this mentee"))
                } else {
                    val breakdown = submissions
                        .groupBy { it.type }
                        .mapValues { entry ->
                            entry.value.map { it.score }.average()
                        }
                    Result.success(breakdown)
                }
            },
            onFailure = { error -> Result.failure(error) }
        )
    }
}