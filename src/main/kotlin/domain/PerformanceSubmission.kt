package domain

data class PerformanceSubmission(
    val menteeId: String,
    val submissionId: String,
    val submissionType: String,
    val score: String
)