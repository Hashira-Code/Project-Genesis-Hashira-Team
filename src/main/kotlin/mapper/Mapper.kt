package mapper


interface Mapper<T,R> {
    fun toDaomain(rawList:List<T>):List<R>
}