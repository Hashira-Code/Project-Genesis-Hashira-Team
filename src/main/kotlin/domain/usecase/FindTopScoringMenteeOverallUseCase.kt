package domain.usecase
import domain.model.Mentee
import domain.repository.PerformanceRepo
import domain.repository.MenteeRepo
class FindTopScoringMenteeOverallUseCase(
    private val performanceRepo: PerformanceRepo,
    private val menteeRepo: MenteeRepo
) {
    operator fun invoke(): Mentee? {
        val topMenteeId = findTopMenteeId()
        return getMenteeById(topMenteeId)
    }
    private fun findTopMenteeId(): String? {
        val performances = performanceRepo.getAll()
        var topMenteeId: String? = null
        var highestAverage = 0.0

        performances.asSequence()
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
        return menteeRepo.getById(menteeId)
    }
}