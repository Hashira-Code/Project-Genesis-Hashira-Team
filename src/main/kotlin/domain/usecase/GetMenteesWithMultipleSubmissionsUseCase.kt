package domain.usecase

import domain.model.entity.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetMenteesWithMultipleSubmissionsUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {
    operator fun invoke(): Result<List<String>> {
        val mentees = menteeRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val submissions = performanceRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val ids = getMenteeIdsWithMultipleSubmissions(submissions)
        val result = mentees
            .filter { it.id in ids }
            .map { it.name }

        return Result.success(result)
    }

    private fun getMenteeIdsWithMultipleSubmissions(submissions: List<PerformanceSubmission>): Set<String> {
        return submissions
            .groupingBy { it.menteeId }
            .eachCount()
            .filter { it.value > MINIMUM_MULTIPLE_SUBMISSIONS }
            .keys

    }

    companion object {
        private const val MINIMUM_MULTIPLE_SUBMISSIONS = 1

    }
}