
package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.SubmissionType
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetTopPerformingMenteesBySubmissionTypeUseCase(
    private val performanceRepo: PerformanceRepo,
    private val menteeRepo: MenteeRepo
) {
    operator fun invoke(type: SubmissionType): Result<Mentee?> {
        return TODO("TDD")
    }
}