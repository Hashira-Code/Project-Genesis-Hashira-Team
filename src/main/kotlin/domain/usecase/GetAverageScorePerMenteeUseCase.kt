package domain.usecase

import domain.model.Mentee
import domain.model.MenteeAverageScore
import domain.model.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetAverageScorePerMenteeUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {

    operator fun invoke(): List<MenteeAverageScore> {
        val allMentees = menteeRepo.getAll()
        val allSubmissions = performanceRepo.getAll()

        val averageScoreByMenteeId =
            calculateAverageScore(allSubmissions)

        return mapMenteesToAverageScore(
            allMentees,
            averageScoreByMenteeId
        )
    }

    private fun calculateAverageScore(
        submissions: List<PerformanceSubmission>
    ): Map<String, Double> =
        submissions.asSequence()
            .groupBy { it.menteeId }
            .mapValues { entry ->
                entry.value.map { it.score }.average()
            }

    private fun mapMenteesToAverageScore(
        mentees: List<Mentee>,
        averageScoreByMenteeId: Map<String, Double>
    ): List<MenteeAverageScore> =
        mentees.asSequence()
            .filter { it.id in averageScoreByMenteeId.keys }
            .map { MenteeAverageScore(it.name, averageScoreByMenteeId[it.id]!!) }
            .toList()
}
