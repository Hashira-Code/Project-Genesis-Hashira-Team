package domain.model

enum class AttendanceStatus {
    PRESENT,
    ABSENT,
    LATE,
    UNKNOWN;


    companion object {

        fun fromString(value: String): AttendanceStatus {
            val normalized = value.trim().uppercase()
            return when (normalized) {
                "PRESENT" -> PRESENT
                "ABSENT" -> ABSENT
                "LATE" -> LATE
                else -> UNKNOWN
            }
        }
    }
}

