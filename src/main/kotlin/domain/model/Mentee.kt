package domain.model

import domain.model.exception.ValidationException.InvalidFormatException
import domain.model.exception.ValidationException.MenteeIdEmptyException


data class Mentee private constructor(
    val id: String,
    val name: String,
    val teamId: String,
) {
    companion object {
        const val EMPTY_MSG = "Mentee ID cannot be blank"
        const val INVALID_FORMAT_MSG = "Mentee ID must start with 'm' followed by digits"
        private val VALID_PATTERN = Regex("^m\\d+$")

        fun create(id: String, name: String, teamId: String): Mentee {
            val validatedId = validate(id)

            return Mentee(validatedId, name, teamId)
        }

        private fun validate(value: String): String {
            return validateIdPattern(value)
        }

        private fun validateIdPattern(menteeId: String): String {
            if (menteeId.isBlank()) throw MenteeIdEmptyException(EMPTY_MSG)
            if (!VALID_PATTERN.matches(menteeId)) throw InvalidFormatException(INVALID_FORMAT_MSG)
            return menteeId
        }


    }
}
