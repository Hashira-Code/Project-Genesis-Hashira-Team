package domain.validation

import domain.exception.WeekNumberValidationException

class WeekNumberValidator : Validator<Int, Int> {
    override fun validate(value: Int): Result<Int> {
        return if (value != 0) {
            Result.success(value)
        } else {
            Result.failure(
                WeekNumberValidationException("Week number cannot be zero"))
        }
    }
}

