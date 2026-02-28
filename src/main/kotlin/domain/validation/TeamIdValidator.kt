package domain.validation

import domain.exception.IdValidationException

class TeamIdValidator : Validator<String, String> {
    override fun validate(value: String): Result<String> =
        value.takeIf { it.isNotBlank() }
            ?.let { it.takeIf { it.matches(VALID_PATTERN) } }
            ?.let { Result.success(it) }
            ?: Result.failure(
                IdValidationException(
                    when {
                        value.isBlank() -> EMPTY_MSG
                        else -> INVALID_CHAR_MSG
                    }
                )
            )

    companion object {
        const val EMPTY_MSG = "Team ID cannot be empty"
        const val INVALID_CHAR_MSG = "Team ID must contain only lowercase letters and hyphens"
        private val VALID_PATTERN = Regex("^[a-z-]+$")
    }
}
