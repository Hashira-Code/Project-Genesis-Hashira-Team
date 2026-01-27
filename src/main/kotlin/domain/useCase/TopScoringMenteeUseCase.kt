package domain.useCase
import domain.model.Mentee
import domain.repository.PerformanceRepo
import domain.repository.MenteeRepo
class FindTopScoringMenteeOverallUseCase(
    private val performanceRepo: PerformanceRepo,
    private val menteeRepo: MenteeRepo
) {
    fun execute(): Mentee? {
        val topMenteeId = findTopMenteeId()
        return getMenteeById(topMenteeId)
    }
    private fun findTopMenteeId(): String? {
        val performances = performanceRepo.getAll()
        var topMenteeId: String? = null
        var highestAverage = 0.0

        performances
            .groupBy { it.menteeId }
            .forEach { (menteeId, submissions) ->
                val averageScore = submissions.map { it.score }.average()
                if (averageScore > highestAverage) {
                    highestAverage = averageScore
                    topMenteeId = menteeId
                }
            }
        return topMenteeId
    }
    private fun getMenteeById(menteeId: String?): Mentee? {
        if (menteeId == null) return null
        var result: Mentee? = null
        menteeRepo.getAll().forEach { mentee ->
            if (mentee.id == menteeId) {
                result = mentee
            }
        }
        return result
    }
}