package domain.model.exception

open class ValidationExeption(message: String) : Exception(message) {
    class EmptyFieldExeption(message: String) : ValidationExeption(message)
    class InvalidFormatExeption(message: String) : ValidationExeption(message)
    class ValueOutOfRangeExeption(message: String) : ValidationExeption(message)
    class EntityNotFoundExeption(message: String) : ValidationExeption(message)
    class MenteeIdEmptyExeption(message: String) : ValidationExeption(message)
    class InvalidNameLengthExeption(message: String) : ValidationExeption(message)
    class DataNotFoundExeption(message: String) : ValidationExeption(message)
}




