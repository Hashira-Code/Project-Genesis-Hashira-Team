package domain.model.exception

sealed class DataSourceException(message: String) : Exception(message){
    class DataSourceNotFound(message:String) : DataSourceException(message)
    class InvalidDataSource(message:String) : DataSourceException(message)
}