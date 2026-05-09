package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class FindTopScoringMenteeOverallUseCase(
    private val performanceRepo: PerformanceRepo,
    private val menteeRepo: MenteeRepo
) {
    suspend operator fun invoke(): Result<Mentee?> = coroutineScope {
        val menteesDeferred = async { menteeRepo.getAll() }
        val performancesDeferred = async { performanceRepo.getAll() }

        val mentees = menteesDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val performances = performancesDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }

        val topMenteeId = findOverallTopMenteeId(performances)
        val topMentee = mentees.find { mentee -> mentee.id == topMenteeId }

        Result.success(topMentee)
    }

    private fun findOverallTopMenteeId(
        performances: List<PerformanceSubmission>
    ): String? {
        return performances
            .groupBy { it.menteeId }
            .maxByOrNull { (_, submissions) ->
                submissions.map { it.score }.average()
            }
            ?.key
    }
}
