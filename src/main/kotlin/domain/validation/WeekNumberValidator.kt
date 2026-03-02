package domain.validation

import domain.model.exception.ValueOutOfRangeException

class WeekNumberValidator : Validator<Int, Int> {
    override fun validate(value: Int): Result<Int> {
        if (value <= MIN_WEEK) {
            return Result.failure(ValueOutOfRangeException(NON_POSITIVE_ERROR))
        }
        return Result.success(value)
    }

    companion object {
        private const val MIN_WEEK = 0
        const val NON_POSITIVE_ERROR = "Week number must be greater than zero"
    }
}