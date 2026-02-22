package domain.validation

import domain.exception.IdValidationException
import domain.exception.WeekNumberValidationException

class WeekNumberValidator : Validator<String, Int> {
    override fun validate(value: String): Result<Int> {
        if (value.isBlank()) {
            return Result.failure(
                WeekNumberValidationException("Week number cannot be empty")
            )
        }
        val weekNumber = value.toIntOrNull() ?:
        return Result.failure(
            WeekNumberValidationException("Week number must be valid"))

        if (weekNumber <= 0) {
            return Result.failure(
                WeekNumberValidationException("Week must be greater than zero"))

        }
        return Result.success(weekNumber)
    }

}

