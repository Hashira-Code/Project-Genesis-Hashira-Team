package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetMenteesWithoutAnySubmissionUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    suspend operator fun invoke(): Result<List<String>> = coroutineScope {
        val allMenteesDeferred = async { menteeRepo.getAll() }
        val allSubmissionsDeferred = async { performanceRepo.getAll() }

        val allMentees = allMenteesDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val allSubmissions = allSubmissionsDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val menteesWhoSubmittedWork =
            extractMenteesWhoSubmittedWork(allSubmissions)

        val result = filterMenteesWhoNeverSubmitted(
            allMentees,
            menteesWhoSubmittedWork
        )
        Result.success(result)
    }

    private fun extractMenteesWhoSubmittedWork(
        submissions: List<PerformanceSubmission>
    ): Set<String> =
        submissions
            .map { it.menteeId }
            .toSet()

    private fun filterMenteesWhoNeverSubmitted(
        mentees: List<Mentee>,
        menteesWhoSubmittedWork: Set<String>
    ): List<String> =
        mentees
            .filter { it.id !in menteesWhoSubmittedWork }
            .map { it.name }
}


