package domain

data class PerformanceSubmission(
    val id: String,
    val type: String,
    val score: String,
    val menteeId: String
)