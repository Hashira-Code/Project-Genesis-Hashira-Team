package data.dataSource

import data.exception.CsvException
import data.validation.Validator
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CsvFileReader(
    private val path: String,
    private val fileValidator: Validator<File>,
    private val lineValidator: Validator<String>
) {

    suspend fun readLines(
        resource: String
    ): List<List<String>> =
        withContext(Dispatchers.IO) {
            val file = validateFile(resource)

            file.readLines()
                .drop(HEADER_LINES)
                .mapIndexed { index, line -> validateLine(line, resource, index) }
                .map { line -> line.split(DELIMITER).map { it.trim() } }
        }

    private fun validateFile(
        resource: String
    ): File {
        val file = File("$path/$resource")
        fileValidator.validate(file).getOrElse {
            throw CsvException.FileNotValidException(
                buildErrorMessage
                    (resource, NO_LINE, CsvException.FILE_NOT_VALID)
            )
        }
        return file
    }

    private fun validateLine(
        line: String,
        resource: String,
        index: Int
    ): String {
        return lineValidator.validate(line).getOrElse {
            throw CsvException.EmptyLineException(
                buildErrorMessage
                    (resource, index + DATA_START_LINE, CsvException.EMPTY_LINE)
            )
        }
    }

    private fun buildErrorMessage(
        resource: String,
        lineNumber: Int,
        message: String
    ): String =
        "Error in file '$resource' at line $lineNumber: $message"

    companion object {
        private const val HEADER_LINES = 1
        private const val DATA_START_LINE = 2
        private const val NO_LINE = 0
        private const val DELIMITER = ","
    }
}