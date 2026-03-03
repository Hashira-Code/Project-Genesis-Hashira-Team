package data.validation

class LineIsNotEmptyValidator : Validator<String> {

    override fun validate(input: String): Result<String> {
        if (input.isBlank()) {
            return Result.failure(
                CsvException.EmptyLineException(ERROR_MESSAGE))
        }
        return Result.success(input)

    }
    companion object {
        private const val ERROR_MESSAGE = "Line is empty"
    }
}