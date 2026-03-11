package domain.usecase

import domain.model.entity.SubmissionType
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class FindMenteesNamesWithTasksUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {
    operator fun invoke(): Result<List<String>> {
        val taskSubmissions = performanceRepo.getByType(SubmissionType.TASK).getOrElse {
            return Result.failure(it)
        }

        if (taskSubmissions.isEmpty()) return Result.success(emptyList())

        val menteeIdsWithTasks = taskSubmissions
            .asSequence()
            .map { it.menteeId }
            .toSet()

        val menteeNames = menteeRepo.getAll().getOrElse {
            return Result.failure(it)
        }
            .asSequence()
            .filter { it.id in menteeIdsWithTasks }
            .map  { it.name }
            .toList()

        return Result.success(menteeNames)
    }
}
