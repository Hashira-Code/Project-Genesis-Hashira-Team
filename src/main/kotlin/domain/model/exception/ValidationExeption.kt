package domain.model.exception

open class ValidationException(message: String) : Exception(message) {
    class EmptyFieldException(message: String) : ValidationException(message)
    class InvalidFormatException(message: String) : ValidationException(message)
    class ValueOutOfRangeException(message: String) : ValidationException(message)
    class EntityNotFoundException(message: String) : Exception(message)
    class MenteeIdEmptyException(message: String) : Exception(message)
    class InvalidNameLengthException(message: String) : Exception(message)
}




