package domain.usecase

import domain.model.Mentee
import domain.model.PerformanceSubmission
import domain.model.SubmissionType
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetMenteesOrderedByTaskCountUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    operator fun invoke(): List<String> {
        val allMentees = menteeRepo.getAll()
        val allSubmissions = performanceRepo.getAll()

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
