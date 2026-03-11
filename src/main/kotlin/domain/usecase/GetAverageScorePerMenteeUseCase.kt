package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetAverageScorePerMenteeUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    operator fun invoke(): List<Pair<String, Double>> {
        val allMentees = menteeRepo.getAll().getOrThrow()
        val allSubmissions = performanceRepo.getAll().getOrThrow()

        val averageScorePerMentee =
            calculateAverageScore(allSubmissions)

        return mapMenteesToAverageScore(
            allMentees,
            averageScorePerMentee
        )
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


