package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetMenteesOrderedByTaskCountUseCase(
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

        val taskCountPerMentee =
            calculateTaskCount(allSubmissions)

        val result = orderMenteesByTaskCount(
            allMentees,
            taskCountPerMentee
        )
        Result.success(result)
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
            .sortedByDescending { taskCountPerMentee[it.id] ?: 0 }
            .map { it.name }
}
