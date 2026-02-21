package domain.validation

import domain.exception.IdValidationException

class MenteeIdValidator : Validator<String,String> {
    override fun validate(value: String): Result<String> {
        if (value.isBlank()) {
            return Result.failure(IdValidationException("Mentee ID Cannot be blank"))
        }
        if (!value.matches(Regex("^m\\d+$"))) {
            return Result.failure(IdValidationException("Mentee ID must start wiht 'm' followed by digits"))

        }
        return Result.success(value)

    }

}