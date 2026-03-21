package domain.usecase

import domain.model.Mentee
import domain.model.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetAverageScorePerMenteeUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    operator fun invoke(): List<Pair<String, Double>> {
        val allMentees = menteeRepo.getAll()
        val allSubmissions = performanceRepo.getAll()

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
