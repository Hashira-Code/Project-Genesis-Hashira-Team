package data.exception

import domain.model.exception.DataSourceException

fun CsvException.toDomain(): DataSourceException = when (this) {
    is CsvException.FileNotFoundException -> DataSourceException.DataSourceNotFound(
        message ?: CsvException.FILE_NOT_FOUND
    )

    is CsvException.FileNotValidException -> DataSourceException.InvalidDataSource(
        message ?: CsvException.FILE_NOT_VALID
    )

    is CsvException.EmptyLineException -> DataSourceException.InvalidDataSource(
        message ?: CsvException.EMPTY_LINE
    )

    is CsvException.MissingColumnsException -> DataSourceException.InvalidDataSource(
        message ?: CsvException.MISSING_COLUMNS
    )

    is CsvException.EmptyFieldException -> DataSourceException.InvalidDataSource(
        message ?: CsvException.EMPTY_FIELD
    )

    is CsvException.InvalidEnumException -> DataSourceException.InvalidDataSource(
        message ?: CsvException.INVALID_ENUM
    )

    else -> DataSourceException.InvalidDataSource(
        message ?: CsvException.DEFAULT_MESSAGE
    )
}

fun <T> Result<T>.mapCsvErrorToDomain(): Result<T> =
    fold(
        onSuccess = { Result.success(it) },
        onFailure = { error ->
            Result.failure(
                when (error) {
                    is CsvException -> error.toDomain()
                    else -> DataSourceException.InvalidDataSource(error.message ?: CsvException.DEFAULT_MESSAGE)
                }
            )
        }
    )