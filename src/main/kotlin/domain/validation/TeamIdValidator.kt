package domain.validation

import domain.model.exception.ValidationException.EmptyFieldException
import domain.model.exception.ValidationException.InvalidFormatException

class TeamIdValidator : Validator<String, String> {
    override fun validate(value: String): Result<String> {
        if (value.isBlank()) {
            return Result.failure(
                EmptyFieldException(EMPTY_MSG)
            )
        }
        if (!VALID_PATTERN.matches(value)) {
            return Result.failure(
                InvalidFormatException(INVALID_CHAR_MSG)
            )
        }
        return Result.success(value)
    }

    companion object {
        const val EMPTY_MSG = "Team ID cannot be empty"
        const val INVALID_CHAR_MSG = "Team ID must contain only lowercase letters and hyphens"
        private val VALID_PATTERN = Regex("^[a-z-]+$")
    }
}

