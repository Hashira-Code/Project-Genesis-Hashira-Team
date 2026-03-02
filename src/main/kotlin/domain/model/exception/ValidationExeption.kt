package domain.model.exception

open class ValidationException(message: String) : Exception(message) {
    class IdValidationException(message: String) : ValidationException(message)
    class NameValidationException(message: String) : ValidationException(message)
    class ScoreValidationException(message: String) : ValidationException(message)
    class WeekNumberValidationException(message: String) : ValidationException(message)
}
class EmptyFieldException(message: String = "Field cannot be empty") : ValidationException(message)
class InvalidFormatException(message: String = "Invalid format") : ValidationException(message)
class ValueOutOfRangeException(message: String = "Value out of allowed range") : ValidationException(message)
class EntityNotFoundException(message: String) : Exception(message)
class MenteeIdEmptyException(message: String): Exception(message)


