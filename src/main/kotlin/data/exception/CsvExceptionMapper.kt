package data.exception

import domain.model.exception.DomainDataSourceException

fun CsvException.toDomain(): DomainDataSourceException = when (this) {
    is CsvException.FileNotFoundException -> DomainDataSourceException.DataSourceNotFound(
        message ?: CsvException.FILE_NOT_FOUND
    )

    is CsvException.FileNotValidException -> DomainDataSourceException.InvalidDataSource(
        message ?: CsvException.FILE_NOT_VALID
    )

    is CsvException.EmptyLineException -> DomainDataSourceException.InvalidDataSource(
        message ?: CsvException.EMPTY_LINE
    )

    is CsvException.MissingColumnsException -> DomainDataSourceException.InvalidDataSource(
        message ?: CsvException.MISSING_COLUMNS
    )

    is CsvException.EmptyFieldException -> DomainDataSourceException.InvalidDataSource(
        message ?: CsvException.EMPTY_FIELD
    )

    is CsvException.InvalidEnumException -> DomainDataSourceException.InvalidDataSource(
        message ?: CsvException.INVALID_ENUM
    )

    else -> DomainDataSourceException.InvalidDataSource(
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
                    else -> DomainDataSourceException.InvalidDataSource(error.message ?: CsvException.DEFAULT_MESSAGE)
                }
            )
        }
    )