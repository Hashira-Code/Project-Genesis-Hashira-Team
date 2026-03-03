
open class CsvException(message: String) : Exception(message) {
    class FileNotFoundException(message: String) : CsvException(message)
    class FileNotValidException(message: String) : CsvException(message)
    class EmptyLineException(message: String) : CsvException(message)
    class MissingColumnsException(message: String) : CsvException(message)
    class EmptyFieldException(message: String) : CsvException(message)
}
