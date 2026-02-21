package domain.validation

import domain.exception.NameValidationException

class NameValidator : Validator<String, String> {
    override fun validate(value: String): Result<String> {
        if (value.isBlank()) {
            return Result.failure(NameValidationException("Name cannot be blank"))
        }
        if (value.length < 3) {
            return Result.failure(NameValidationException("Name be at least 3 characters"))
        }
        return Result.success(value)


    }
}