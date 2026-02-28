package domain.validation

import domain.exception.WeekNumberValidationException

class WeekNumberValidator : Validator<String, Int> {
    override fun validate(value: String): Result<Int> {
        return value.takeIf { it.isNotBlank() }
            ?.toIntOrNull()
            ?.let { weekNumber ->
                if (weekNumber > MIN_WEEK) {
                    Result.success(weekNumber)
                } else {
                    Result.failure(WeekNumberValidationException(NON_POSITIVE_ERROR))
                }
            } ?: Result.failure(
            WeekNumberValidationException(
                if (value.isBlank()) EMPTY_ERROR else INVALID_ERROR
            )
        )
    }

    companion object {
        private const val EMPTY_ERROR = "Week number cannot be empty"
        private const val INVALID_ERROR = "Week number must be valid"
        private const val NON_POSITIVE_ERROR = "Week must be greater than zero"
        private const val MIN_WEEK = 0
    }
}