import domain.repository.PerformanceRepo
import domain.repository.MenteeRepo
import domain.model.PerformanceSubmission
import domain.model.SubmissionType
import domain.model.Mentee

class FindMenteesNamesWithTasksUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    fun execute(): List<String> {
        val mentees = menteeRepo.getAll()
        val submissions = performanceRepo.getAll()

        return findMenteesNamesWithTasks(
            mentees = mentees,
            submissions = submissions
        )
    }

    private fun findMenteesNamesWithTasks(
        mentees: List<Mentee>,
        submissions: List<PerformanceSubmission>
    ): List<String> {

        val menteeIdsWithTasks = submissions
            .filter { it.type == SubmissionType.TASK }
            .map { it.menteeId }
            .toSet()

        return mentees
            .filter { it.id in menteeIdsWithTasks }
            .map { it.name }
    }
}