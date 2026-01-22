package data.mapper

import data.model.PerformanceRaw
import domain.model.PerformanceSubmission
import domain.model.SubmissionType


class PerformanceMapper() : Mapper<PerformanceRaw ,PerformanceSubmission>{
    override fun toDomain(rawList: List<PerformanceRaw>): List<PerformanceSubmission>{
        return rawList.map { raw ->
            PerformanceSubmission (
                raw.id ,
                raw.menteeId,
                raw.Type.toSubmissionType() ,
                raw.score
            )
        }


    }
}
fun String.toSubmissionType() : SubmissionType {
    return runCatching { SubmissionType.valueOf(this.trim().uppercase()) }
        .getOrElse { SubmissionType.UNKNOWN }
}
