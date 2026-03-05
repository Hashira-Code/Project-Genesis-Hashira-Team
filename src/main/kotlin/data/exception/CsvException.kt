package data.exception

open class CsvException(message: String) : Exception(message) {
    class FileNotFoundException(message: String) : CsvException(message)
    class FileNotValidException(message: String) : CsvException(message)
    class EmptyLineException(message: String) : CsvException(message)
    class MissingColumnsException(message: String) : CsvException(message)
    class EmptyFieldException(message: String) : CsvException(message)
    class InvalidEnumException(message: String) : CsvException(message)

    companion object {
        const val DEFAULT_MESSAGE = "Unknown CSV error"
        const val FILE_NOT_FOUND = "File not found"
        const val FILE_NOT_VALID = "Invalid file"
        const val EMPTY_LINE = "Empty line"
        const val MISSING_COLUMNS = "Missing columns"
        const val EMPTY_FIELD = "Empty field"
        const val INVALID_ENUM = "Invalid enum"
    }
}
