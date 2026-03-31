package domain.model.entity

import domain.model.exception.ValidationExeption.EmptyFieldExeption
import domain.model.exception.ValidationExeption.InvalidFormatExeption

data class Team private constructor(
    val id: String,
    val name: String,
    val mentorLead: String,
) {
    companion object {
         const val EMPTY_MSG = "Team ID cannot be empty"
         const val INVALID_CHAR_MSG = "Team ID must contain only lowercase letters and hyphens"
        private val VALID_PATTERN = Regex("^[a-z-]+$")

        fun create(id: String, name: String, mentorLead: String): Team {
            val validatedId = validateID(id)
            return Team(validatedId, name, mentorLead)
        }

        private fun validateID(value: String): String = validateIdPattern(value)

        private fun validateIdPattern(teamId: String): String {
            if (teamId.isBlank()) {
                throw EmptyFieldExeption(EMPTY_MSG)
            }
            if (!VALID_PATTERN.matches(teamId)) {
                throw InvalidFormatExeption(INVALID_CHAR_MSG)
            }
            return teamId
        }
    }
}

