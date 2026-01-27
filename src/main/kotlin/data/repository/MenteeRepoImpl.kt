package data.repository

import data.source.DataSource
import domain.model.Mentee
import domain.repository.MenteeRepo
import data.mapper.*
import data.model.MenteeRaw

class MenteeRepoImpl(
    private val dataSource: DataSource,
    private val mapper: Mapper<MenteeRaw, Mentee>
) : MenteeRepo {


    private val cache: List<Mentee> by lazy {
        mapper.toDomain(dataSource.getAllMentees())
    }



    override fun getAll(): List<Mentee> = cache


}