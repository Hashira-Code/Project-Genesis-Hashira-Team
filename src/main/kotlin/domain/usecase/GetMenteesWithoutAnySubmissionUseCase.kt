package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetMenteesWithoutAnySubmissionUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    operator fun invoke(): List<String> {
        val allMentees = menteeRepo.getAll().getOrThrow()
        val allSubmissions = performanceRepo.getAll().getOrThrow()

        val menteesWhoSubmittedWork =
            extractMenteesWhoSubmittedWork(allSubmissions)

        return filterMenteesWhoNeverSubmitted(
            allMentees,
            menteesWhoSubmittedWork
        )
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


