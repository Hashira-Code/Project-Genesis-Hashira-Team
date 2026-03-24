package domain.model.entity

import domain.model.exception.ValidationException.EmptyFieldException
import domain.model.exception.ValidationException.InvalidFormatException

data class Project private constructor(
    val id: String,
    val name: String,
    val teamId: String
) {
    companion object {
        const val EMPTY_PROJECT_ID_MSG = "Project ID cannot be empty"
        const val INVALID_PROJECT_ID_MSG = "Project ID must start with 'p' and be followed by digits only"

        const val EMPTY_TEAM_ID_MSG = "Team ID cannot be empty"
        const val INVALID_TEAM_ID_MSG = "Team ID must contain only lowercase letters and hyphens"

        private val PROJECT_ID_PATTERN = Regex("^p\\d+$")
        private val TEAM_ID_PATTERN = Regex("^[a-z-]+$")

        fun create(id: String, name: String, teamId: String): Project {
            val validatedProjectId = validateID(id)
            val validatedTeamId = validateTeamId(teamId)
            return Project(validatedProjectId, name, validatedTeamId)
        }

        private fun validateID(value: String): String {
            return validateIdFormat(value)
        }

        private fun validateIdFormat(value: String): String {
            if (value.isBlank()) {
                throw EmptyFieldException(EMPTY_PROJECT_ID_MSG)
            }
            if (!PROJECT_ID_PATTERN.matches(value)) {
                throw InvalidFormatException(INVALID_PROJECT_ID_MSG)
            }
            return value
        }

        private fun validateTeamId(value: String): String {
            return validateTeamIdFormat(value)
        }

        private fun validateTeamIdFormat(value: String): String {
            if (value.isBlank()) {
                throw EmptyFieldException(EMPTY_TEAM_ID_MSG)
            }
            if (!TEAM_ID_PATTERN.matches(value)) {
                throw InvalidFormatException(INVALID_TEAM_ID_MSG)
            }
            return value
        }
    }
}

