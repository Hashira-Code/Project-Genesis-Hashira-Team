package data.exception

import domain.model.exception.DomainDataException

fun CsvException.toDomain(): DomainDataException = when (this) {
    is CsvException.FileNotFoundException -> DomainDataException.FileNotFound(
        message ?: CsvException.FILE_NOT_FOUND
    )

    is CsvException.FileNotValidException -> DomainDataException.InvalidFile(
        message ?: CsvException.FILE_NOT_VALID
    )

    is CsvException.EmptyLineException -> DomainDataException.EmptyLine(
        message ?: CsvException.EMPTY_LINE
    )

    is CsvException.MissingColumnsException -> DomainDataException.MissingColumns(
        message ?: CsvException.MISSING_COLUMNS
    )

    is CsvException.EmptyFieldException -> DomainDataException.EmptyField(
        message ?: CsvException.EMPTY_FIELD
    )

    is CsvException.InvalidEnumException -> DomainDataException.InvalidEnum(
        message ?: CsvException.INVALID_ENUM
    )

    else -> DomainDataException.Unknown(
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
                    else -> DomainDataException.Unknown(error.message ?: "Unknown error")
                }
            )
        }
    )