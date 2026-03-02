package domain.validation

import domain.model.exception.InvalidFormatException
import domain.model.exception.MenteeIdEmptyException


class MenteeIdValidator : Validator<String, String> {
    override fun validate(value: String): Result<String> {
        if (value.isBlank()) {
            return Result.failure(
                MenteeIdEmptyException(EMPTY_MSG)
            )
        }
        if (!VALID_PATTERN.matches(value)) {
            return Result.failure(
                InvalidFormatException(INVALID_FORMAT_MSG)
            )
        }
        return Result.success(value)

    }

    companion object {
        const val EMPTY_MSG = "Mentee ID cannot be blank"
        const val INVALID_FORMAT_MSG = "Mentee ID must start with 'm' followed by digits"
        private val VALID_PATTERN = Regex("^m\\d+$")
    }
}

