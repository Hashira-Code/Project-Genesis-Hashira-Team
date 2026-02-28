 package domain.validation
 import domain.exception.IdValidationException

 class MenteeIdValidator : Validator<String,String> {
            override fun validate(value: String): Result<String> =
                value.takeIf { it.isNotBlank() }
                    ?.takeIf { VALID_PATTERN.matches(it) }
                    ?.let { Result.success(it) }
                    ?: Result.failure(
                        IdValidationException(
                            when {
                                value.isBlank() -> EMPTY_MSG
                                else -> INVALID_FORMAT_MSG
                            }
                        )
                    )

            companion object {
                const val EMPTY_MSG = "Mentee ID cannot be blank"
                const val INVALID_FORMAT_MSG = "Mentee ID must start with 'm' followed by digits"
                private val VALID_PATTERN = Regex("^m\\d+$")
            }
        }

}