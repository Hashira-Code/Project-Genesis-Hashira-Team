package data.validation

class LineIsNotEmptyValidator : Validator<String> {

    override fun validate(input: String): Result<String> =
        input.takeIf { it.isNotBlank() }
            ?.let { Result.success(it) }
            ?: Result.failure(IllegalArgumentException(ERROR_MESSAGE))

    companion object {
        private const val ERROR_MESSAGE = "Line is empty"
    }
}