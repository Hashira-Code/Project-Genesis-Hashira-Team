package data.dataSource
import data.exception.CsvException
import data.validation.MissingColumnsValidator
import data.validation.Validator

class CsvRowParser(
    private val emptyFieldValidator: Validator<List<String>>
) {

    suspend fun  <T> parseRows(
        rows: List<List<String>>,
        resource: String,
        expectedColumns: Int,
        mapper: (List<String>) -> T
    ): List<T> {
        val columnsValidator = MissingColumnsValidator(expectedColumns)
        return rows.mapIndexed { index, row ->
            val lineNumber = index + DATA_START_LINE
            validateColumns(row, columnsValidator, resource, lineNumber)
            validateEmptyFields(row, resource, lineNumber)
            mapper(row)
        }
    }

    private fun validateColumns(
        row: List<String>,
        columnsValidator: MissingColumnsValidator,
        resource: String,
        lineNumber: Int
    ) {
        columnsValidator.validate(row).getOrElse {
            throw CsvException.MissingColumnsException(
                buildErrorMessage(resource, lineNumber, CsvException.MISSING_COLUMNS)
            )
        }
    }

    private fun validateEmptyFields(
        row: List<String>,
        resource: String,
        lineNumber: Int
    ) {
        emptyFieldValidator.validate(row).getOrElse {
            throw CsvException.EmptyFieldException(
                buildErrorMessage(resource, lineNumber, CsvException.EMPTY_FIELD)
            )
        }
    }

    private fun buildErrorMessage(
        resource: String,
        lineNumber: Int,
        message: String): String =
        "Error in file '$resource' at line $lineNumber: $message"

    companion object {
        private const val DATA_START_LINE = 2
    }
}
