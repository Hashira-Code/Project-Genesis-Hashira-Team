package domain.model

data class Attendance(
    val menteeId: String,
    val weekNumber: Int,
    val status: AttendanceStatus
)

