package data.validation

import java.io.File

class FileValidator : Validator<File> {
    override fun validate(input: File): Result<File> {

        if (!input.exists()) {
            return Result.failure(
                CsvException.FileNotFoundException("${ERROR_FILE_NOT_EXIST}${input.path}")
            )
        }
        if (!input.isFile) {
            return Result.failure(
                CsvException.FileNotValidException("${ERROR_NOT_A_FILE}${input.path}")
            )
        }
        if (!input.extension.equals(CSV_EXTENSION, ignoreCase = true)) {
            return Result.failure(
                CsvException.FileNotValidException("${ERROR_NOT_CSV}${input.path}")
            )
        }
        if (input.length() == 0L) {
            return Result.failure(
                CsvException.FileNotValidException("${ERROR_FILE_EMPTY}${input.path}")
            )
        }
        return Result.success(input)
    }


    companion object {
        private const val CSV_EXTENSION = "csv"
        private const val ERROR_FILE_NOT_EXIST = "File does not exist: "
        private const val ERROR_NOT_A_FILE = "Path is not a file: "
        private const val ERROR_NOT_CSV = "File must be CSV: "
        private const val ERROR_FILE_EMPTY = "File is empty: "
    }
}
