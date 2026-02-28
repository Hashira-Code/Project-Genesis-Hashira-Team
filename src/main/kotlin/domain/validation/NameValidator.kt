package domain.validation

import domain.exception.NameValidationException

class NameValidator : Validator<String, String> {
    override fun validate(value: String): Result<String> {

        value.takeIf { it.isNotBlank() }
                    ?.takeIf { it.length >= MIN_LENGTH }
                    ?.let { Result.success(it) }
                    ?: Result.failure(
                        NameValidationException(
                            when {
                                value.isBlank() -> EMPTY_MSG
                                else -> LENGTH_MSG
                            }
                        )
                    )

            companion object {
                private const val MIN_LENGTH = 3
                private const val EMPTY_MSG = "Name cannot be blank"
                private const val LENGTH_MSG = "Name must be at least 3 characters"
            }
        }
}