package data.validation

import data.exception.CsvException

class MissingColumnsValidator(
    private val expectedColumnCount: Int
) : Validator<List<String>> {

    override fun validate(input: List<String>): Result<List<String>> {
        if (input.size < expectedColumnCount) {
            return Result.failure(
                CsvException.MissingColumnsException(
                    "$ERROR_MESSAGE $expectedColumnCount $ERROR_FOUND ${input.size}"
                )
            )
        }
        return Result.success(input)
    }

    companion object {
        private const val ERROR_MESSAGE = "Expected"
        private const val ERROR_FOUND = "columns but found"
    }
}
