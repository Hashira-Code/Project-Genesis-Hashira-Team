package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetAverageScorePerMenteeUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {
    operator fun invoke(): Result<List<Pair<String, Double>>> {
        val allMentees = menteeRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val allSubmissions = performanceRepo.getAll().getOrElse {
            return Result.failure(it)
        }

        val averageScorePerMentee =
            calculateAverageScore(allSubmissions)

        val averageScores = mapMenteesToAverageScore(
            allMentees,
            averageScorePerMentee
        )

        return Result.success(averageScores)
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


