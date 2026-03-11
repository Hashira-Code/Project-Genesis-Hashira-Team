package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetMenteesOrderedByTaskCountUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    operator fun invoke(): List<String> {
        val allMentees = menteeRepo.getAll().getOrThrow()
        val allSubmissions = performanceRepo.getAll().getOrThrow()

        val taskCountPerMentee =
            calculateTaskCount(allSubmissions)

        return orderMenteesByTaskCount(
            allMentees,
            taskCountPerMentee
        )
    }

    private fun calculateTaskCount(
        submissions: List<PerformanceSubmission>
    ): Map<String, Int> =
        submissions
            .filter { it.type == SubmissionType.TASK }
            .groupingBy { it.menteeId }
            .eachCount()

    private fun orderMenteesByTaskCount(
        mentees: List<Mentee>,
        taskCountPerMentee: Map<String, Int>
    ): List<String> =
        mentees
            .filter { it.id in taskCountPerMentee.keys }
            .sortedByDescending { taskCountPerMentee[it.id] }
            .map { it.name }
}


