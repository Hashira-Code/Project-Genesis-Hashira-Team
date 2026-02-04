package data.mapper

import data.model.PerformanceRaw
import domain.model.PerformanceSubmission
import domain.model.SubmissionType


class PerformanceMapper() : Mapper<PerformanceRaw ,PerformanceSubmission>{
    override fun toDomain(rawList: List<PerformanceRaw>): List<PerformanceSubmission>{
        return rawList.map { raw ->
            require(raw.score >= 0)
            "Score cannot be negative: ${raw.score}"

            PerformanceSubmission (
                raw.id ,
                raw.menteeId,
                raw.Type.toSubmissionType() ,
                raw.score
            )
        }


    }
}
fun String.toSubmissionType() : SubmissionType =
    when (this.trim().uppercase()) {
        "TASK" -> SubmissionType.TASK
        "BOOK_CLUB" -> SubmissionType.BOOK_CLUB
        "WORKSHOP" -> SubmissionType.WORKSHOP
        else -> throw IllegalArgumentException("Invalid submission type: $this")
}
