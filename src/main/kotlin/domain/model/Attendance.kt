package domain.model

data class Attendance(
    val menteeId: String,
    val sessionId: String,
    val status: String
)