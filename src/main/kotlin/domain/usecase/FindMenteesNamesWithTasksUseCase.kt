import domain.repository.PerformanceRepo
import domain.repository.MenteeRepo
import domain.model.entity.SubmissionType

class FindMenteesNamesWithTasksUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    operator fun invoke(): List<String> {

        val taskSubmissions = performanceRepo
            .getByType(SubmissionType.TASK).getOrThrow()

        if (taskSubmissions.isEmpty()) return emptyList()

        val menteeIdsWithTasks = taskSubmissions
            .asSequence()
            .map { it.menteeId }
            .toSet()

        return menteeRepo.getAll().getOrThrow()
            .asSequence()
            .filter { it.id in menteeIdsWithTasks }
            .map { it.name }
            .toList()
    }
}


