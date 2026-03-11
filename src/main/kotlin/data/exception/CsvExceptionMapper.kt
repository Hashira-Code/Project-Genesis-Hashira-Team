package data.exception

import domain.model.exception.DomainDataSourceException

fun CsvException.toDomain(): DomainDataSourceException = when (this) {
    is CsvException.FileNotFoundException -> DomainDataSourceException.FileNotFound(
        message ?: CsvException.FILE_NOT_FOUND
    )

    is CsvException.FileNotValidException -> DomainDataSourceException.InvalidFile(
        message ?: CsvException.FILE_NOT_VALID
    )

    is CsvException.EmptyLineException -> DomainDataSourceException.EmptyLine(
        message ?: CsvException.EMPTY_LINE
    )

    is CsvException.MissingColumnsException -> DomainDataSourceException.MissingColumns(
        message ?: CsvException.MISSING_COLUMNS
    )

    is CsvException.EmptyFieldException -> DomainDataSourceException.EmptyField(
        message ?: CsvException.EMPTY_FIELD
    )

    is CsvException.InvalidEnumException -> DomainDataSourceException.InvalidEnum(
        message ?: CsvException.INVALID_ENUM
    )

    else -> DomainDataSourceException.Unknown(
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
                    else -> DomainDataSourceException.Unknown(error.message ?: "Unknown error")
                }
            )
        }
    )