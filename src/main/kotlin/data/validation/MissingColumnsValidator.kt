package data.validation

class MissingColumnsValidator(
    private val expectedColumnCount: Int
) : Validator<List<String>> {

    override fun validate(input: List<String>): Result<List<String>> =
        input.takeIf { it.size == expectedColumnCount }
            ?.let { Result.success(it) }
            ?: Result.failure(
                IllegalArgumentException(
                    "$ERROR_MESSAGE. Expected $expectedColumnCount columns but found ${input.size}"
                )
            )

    companion object {
        private const val ERROR_MESSAGE = "Missing column in CSV row"
    }
}
