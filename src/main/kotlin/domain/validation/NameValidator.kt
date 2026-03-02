package domain.validation

import domain.model.exception.EmptyFieldException
import domain.model.exception.InvalidNameLengthException

class NameValidator : Validator<String, String> {
    override fun validate(value: String): Result<String> {

        if (value.isBlank()) {
            return Result.failure(
                EmptyFieldException(EMPTY_MSG)
            )
        }

        if (value.length < MIN_LENGTH) {
            return Result.failure(
                InvalidNameLengthException(LENGTH_MSG)
            )
        }
        return Result.success(value)
    }

    companion object {
        private const val MIN_LENGTH = 2
        private const val EMPTY_MSG = "Name cannot be blank"
        private const val LENGTH_MSG = "Name must be at least 2 characters"
    }
}