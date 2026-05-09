package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetAverageScorePerMenteeUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {
    suspend operator fun invoke(): Result<List<Pair<String, Double>>> = coroutineScope {
        val allMenteesDeferred = async { menteeRepo.getAll() }
        val allSubmissionsDeferred = async { performanceRepo.getAll() }

        val allMentees = allMenteesDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val allSubmissions = allSubmissionsDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }

        val averageScorePerMentee =
            calculateAverageScore(allSubmissions)

        val averageScores = mapMenteesToAverageScore(
            allMentees,
            averageScorePerMentee
        )

        Result.success(averageScores)
    }

    private fun calculateAverageScore(
        submissions: List<PerformanceSubmission>
    ): Map<String, Double> =
        submissions
            .groupBy { it.menteeId }
            .mapValues { entry ->
                entry.value.map { it.score }.average()
            }

    private fun mapMenteesToAverageScore(
        mentees: List<Mentee>,
        averageScorePerMentee: Map<String, Double>
    ): List<Pair<String, Double>> =
        mentees
            .filter { it.id in averageScorePerMentee.keys }
            .map { it.name to averageScorePerMentee[it.id]!! }
}


