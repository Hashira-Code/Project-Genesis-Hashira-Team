package Data.Classes.Files

// Data class to represent a performance record of a mentee, including submission details and score
data class PerformanceRaw(
    val menteeId: String,
    val submissionId: String,
    val submissionType: String,
    val score: String
)

