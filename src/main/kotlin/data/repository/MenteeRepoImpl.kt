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

    private val byId: Map<String, Mentee> by lazy {
        cache.associateBy { it.id }
    }


    private val byTeamId: Map<String, List<Mentee>> by lazy {
        cache.groupBy { it.teamId }
    }

    override fun getAll(): List<Mentee> = cache

    override fun getById(id: String): Mentee? = byId[id]

    override fun getByTeamId(teamId: String): List<Mentee> = byTeamId[teamId].orEmpty()
}