package domain.model.exception

sealed class DomainDataSourceException(message: String) : Exception(message){
    class DataSourceNotFound(message:String) : DomainDataSourceException(message)
    class InvalidDataSource(message:String) : DomainDataSourceException(message)
}