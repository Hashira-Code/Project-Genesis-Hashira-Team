package data.mapper
import data.model.PerformanceRaw
import domain.model.PerformanceSubmission
import domain.model.SubmissionType
class PerformanceMapper {
    fun performanceMapper(raws : List<PerformanceRaw>) : List<PerformanceSubmission>{
       return raws.mapNotNull { raw ->
            PerformanceSubmission(
                raw.id,
                raw.menteeId,
                raw.type.toSubmissionType(),
                raw.score
            )
        }

    }
}
fun String.toSubmissionType() : SubmissionType {
    return  runCatching { SubmissionType.valueOf(this.trim().uppercase() )}.
    getOrElse{ SubmissionType.UNKNOWN }

}