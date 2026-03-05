package domain.model.exception

open class DomainDataException(message: String) : Exception(message){
    class FileNotFound(message:String) : DomainDataException(message)
    class InvalidFile(message:String) : DomainDataException(message)
    class EmptyLine(message:String) : DomainDataException(message)
    class MissingColumns(message:String) : DomainDataException(message)
    class EmptyField(message: String) : DomainDataException(message)
    class InvalidEnum(message: String) : DomainDataException(message)
    class Unknown(message: String) : DomainDataException(message)
}