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
    operator fun invoke(type: SubmissionType): Result<Mentee?> {
        return performanceRepo.getAll().map { submissions ->
            val topMenteeId = findTopScoringMenteeId(submissions, type)
            topMenteeId?.let { id ->
                menteeRepo.getById(id).getOrNull()
            }
        }
    }

    private fun findTopScoringMenteeId(submissions: List<PerformanceSubmission>, type: SubmissionType): String? {
        return submissions.asSequence().filter { it.type == type && it.score >= 0 }
            .maxByOrNull { it.score }
            ?.menteeId
    }

}