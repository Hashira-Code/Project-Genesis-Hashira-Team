package data.validation

class EmptyFieldValidator : Validator<List<String>> {

    override fun validate(input: List<String>): Result<List<String>> {

        if (input.any { it.isBlank() }) {
            return Result.failure(
                CsvException.EmptyFieldException((ERROR_MESSAGE)))
        }
        return Result.success(input)

    }

    companion object {
        private const val ERROR_MESSAGE = "CSV row contains empty field"
    }
}

