package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetMenteesWithoutAnySubmissionUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    operator fun invoke(): Result<List<String>> {
        val allMentees = menteeRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val allSubmissions = performanceRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val menteesWhoSubmittedWork =
            extractMenteesWhoSubmittedWork(allSubmissions)

        val result = filterMenteesWhoNeverSubmitted(
            allMentees,
            menteesWhoSubmittedWork
        )
        return Result.success(result)
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


