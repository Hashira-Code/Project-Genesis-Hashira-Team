package data.validation

class UniqueValueValidator<T>(
    private val seen: MutableSet<T> = mutableSetOf()
) : Validator<T> {

    override fun validate(input: T): Result<T> =
        input.takeIf { seen.add(it) }
            ?.let { Result.success(it) }
            ?: Result.failure(
                IllegalArgumentException("$ERROR_MESSAGE: $input")
            )

    companion object {
        private const val ERROR_MESSAGE = "Duplicate value"
    }
}