package data.validation

class EmptyFieldValidator : Validator<List<String>> {

    override fun validate(input: List<String>): Result<List<String>> =
        input.takeIf { fields -> fields.none { it.isBlank() } }
            ?.let { Result.success(it) }
            ?: Result.failure(
                IllegalArgumentException(ERROR_MESSAGE)
            )

    companion object {
        private const val ERROR_MESSAGE = "CSV row contains empty field"
    }
}
