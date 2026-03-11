package domain.model.entity

data class PerformanceSubmission(
    val id: String,
    val menteeId: String,
    val type: SubmissionType,
    val score: Double
)


