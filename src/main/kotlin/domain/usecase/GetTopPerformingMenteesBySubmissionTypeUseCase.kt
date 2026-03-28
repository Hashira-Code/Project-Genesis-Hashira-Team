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
        val submissions = performanceRepo.getAll().getOrNull() ?: emptyList()
        val mentees = menteeRepo.getAll().getOrNull() ?: emptyList()

        val topSubmission = submissions
            .filter { it.type == type && it.score >= 0 }
            .maxByOrNull { it.score }

        val topMentee = mentees.find { it.id == topSubmission?.menteeId }

        return Result.success(topMentee)
    }
}