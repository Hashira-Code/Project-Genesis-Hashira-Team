package data.repository

import data.dataSource
import domain.model.Mentee
import domain.repository.MenteeRepo
import mapper.*
import dataRaw.menteeRaw

class DefaultMenteeRepository(
    private val dataSource: dataSource,
    private val mapper: Mapper<menteeRaw, Mentee>
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