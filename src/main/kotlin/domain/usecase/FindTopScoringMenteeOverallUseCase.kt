package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class FindTopScoringMenteeOverallUseCase(
    private val performanceRepo: PerformanceRepo,
    private val menteeRepo: MenteeRepo
) {
    operator fun invoke(): Result<Mentee?> {
        val mentees = menteeRepo.getAll().getOrElse { return Result.failure(it) }
        val performances = performanceRepo.getAll().getOrElse { return Result.failure(it) }

        val topMenteeId = findOverallTopMenteeId(performances)
        val topMentee = mentees.find { mentee -> mentee.id == topMenteeId }

        return Result.success(topMentee)
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
