package mapper

import dataRaw.menteeRaw
import domain.model.Mentee


interface Mapper<T,R> {
    fun toDomain(rawList:List<T>):List<R>

}