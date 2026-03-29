package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
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

        val menteeIdsWithTasks = extractUniqueMenteeIds(taskSubmissions)

        return menteeRepo.getAll().map { allMentees ->
            filterMenteeNamesByIds(allMentees, menteeIdsWithTasks)
        }
    }

    private fun extractUniqueMenteeIds(submissions: List<PerformanceSubmission>): Set<String> {
        return submissions
            .asSequence()
            .map { it.menteeId }
            .toSet()
    }

    private fun filterMenteeNamesByIds(mentees: List<Mentee>, ids: Set<String>): List<String> {
        return mentees
            .asSequence()
            .filter { it.id in ids }
            .map { it.name }
            .toList()
    }
}
