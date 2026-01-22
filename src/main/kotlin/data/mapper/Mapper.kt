package data.mapper


interface Mapper<T,R> {
    fun toDomain(rawList:List<T>):List<R>

}