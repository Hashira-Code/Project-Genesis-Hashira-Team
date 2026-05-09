package domain.usecase

import domain.model.entity.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetMenteesWithMultipleSubmissionsUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {
    suspend operator fun invoke(): Result<List<String>> = coroutineScope {
        val menteesDeferred = async { menteeRepo.getAll() }
        val submissionsDeferred = async { performanceRepo.getAll() }

        val mentees = menteesDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val submissions = submissionsDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val ids = getMenteeIdsWithMultipleSubmissions(submissions)
        val result = mentees
            .filter { it.id in ids }
            .map { it.name }

        Result.success(result)
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
