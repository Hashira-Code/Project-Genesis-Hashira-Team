package domain.useCase
import domain.model.Mentee
import domain.repository.PerformanceRepo
import domain.repository.MenteeRepo

class FindTopScoringMenteeOverallUseCase(
    private val performanceRepo: PerformanceRepo,
    private val menteeRepo: MenteeRepo
) {
    fun execute(): Mentee? {
        val performances = performanceRepo.getAll()
        var topMenteeId: String? = null
        var highestAverage = 0.0
        val groupedByMentee = performances.groupBy { it.menteeId }
        groupedByMentee.forEach { (menteeId, submissions) ->
            val averageScore =
                submissions.map { it.score }.average()
            if (averageScore > highestAverage) {
                highestAverage = averageScore
                topMenteeId = menteeId
            }
        }
        var result: Mentee? = null
        menteeRepo.getAll().forEach { mentee ->
            if (mentee.id == topMenteeId) {
                result = mentee
            }
        }
        return result
    }
}