package domain.model

import domain.model.exception.ValidationException.EmptyFieldException
import domain.model.exception.ValidationException.InvalidFormatException
import domain.model.exception.ValidationException.InvalidNameLengthException
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
        private const val NAME_EMPTY_MSG = "Name cannot be blank"
        private const val NAME_LENGTH_MSG = "Name must be at least 2 characters"
        private const val MIN_NAME_LENGTH = 2

        fun create(id: String, name: String, teamId: String): Mentee {
            val validatedId = validateID(id)
            val validatedName = validateName(name)

            return Mentee(validatedId, validatedName, teamId)
        }

        private fun validateID(value: String): String {
            return validateIdFormat(value)
        }

        private fun validateName(value: String) : String {
            return validateNameFormat(value.trim())
        }

        private fun validateIdFormat(menteeId: String): String {
            if (menteeId.isBlank()) throw MenteeIdEmptyException(EMPTY_MSG)
            if (!VALID_PATTERN.matches(menteeId)) throw InvalidFormatException(INVALID_FORMAT_MSG)
            return menteeId
        }
        private fun validateNameFormat(name: String): String {
            if (name.isBlank()) throw EmptyFieldException(NAME_EMPTY_MSG)
            if (name.length < MIN_NAME_LENGTH) throw InvalidNameLengthException(NAME_LENGTH_MSG)
            return name
        }


    }
}
