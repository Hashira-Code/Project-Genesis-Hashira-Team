package domain.model

enum class AttendanceStatus {
    PRESENT,
    ABSENT,
    LATE,
    UNKNOWN;

    fun isAbsent() = this == ABSENT
    fun isPresent() = this == PRESENT
    fun isLate() = this == LATE

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

