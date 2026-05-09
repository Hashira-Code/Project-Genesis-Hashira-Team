package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetTopPerformingMenteesBySubmissionTypeUseCase(
    private val performanceRepo: PerformanceRepo,
    private val menteeRepo: MenteeRepo
) {
    suspend operator fun invoke(type: SubmissionType): Result<Mentee?> {
        val submissions = performanceRepo.getAll().getOrElse {
            return Result.failure(it)
        }

        val topMenteeId = findTopScoringMenteeId(submissions, type)
            ?: return Result.success(null)

        return menteeRepo.getById(topMenteeId)
    }

    private fun findTopScoringMenteeId(submissions: List<PerformanceSubmission>, type: SubmissionType): String? {
        return submissions.asSequence().filter { it.type == type && it.score >= 0 }
            .maxByOrNull { it.score }
            ?.menteeId
    }

}
