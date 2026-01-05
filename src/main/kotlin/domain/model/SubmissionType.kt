package domain.model


enum class SubmissionType {
    TASK,
    BOOK_CLUB,
    WORKSHOP,
    UNKNOWN;

    companion object {

        fun fromString(value: String): SubmissionType {
            val normalized = value.trim()
                .uppercase()


            return when (normalized) {
                "TASK" -> TASK
                "BOOK_CLUB" -> BOOK_CLUB
                "WORKSHOP" -> WORKSHOP
                else -> UNKNOWN
            }
        }
    }
}
