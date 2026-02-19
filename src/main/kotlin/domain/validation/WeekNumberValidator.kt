package domain.validation
import domain.exception.WeekNumberValidationException

    class WeekNumberValidator : Validator<Set<Int>> {
        override fun validate(value: Set<Int>): Result<Set<Int>> {
            return if (value.isNotEmpty()) {
                Result.success(value)
            } else {
                Result.failure(WeekNumberValidationException("No weeks recorded"))
            }
        }
    }

