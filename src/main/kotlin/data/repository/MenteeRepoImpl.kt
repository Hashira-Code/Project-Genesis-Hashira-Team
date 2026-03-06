package data.repository

import data.dataSource.DataSource
import domain.model.Mentee
import domain.repository.MenteeRepo
import data.mapper.*
import data.model.MenteeRaw

class MenteeRepoImpl(
    private val dataSource: DataSource,
    private val mapper: Mapper<MenteeRaw, Mentee>
) : MenteeRepo {


    private val cache: Result<List<Mentee>> by lazy {
        runCatching { mapper.toDomain(dataSource.getAllMentees()) }
    }

    private val byId: Map<String, Mentee> by lazy {
        cache.getOrDefault(emptyList()).associateBy { it.id }
    }

    private val byTeamId: Map<String, List<Mentee>> by lazy {
        cache.getOrDefault(emptyList()).groupBy { it.teamId }
    }

    override fun getAll(): Result<List<Mentee>> = cache

    override fun getById(id: String): Result<Mentee?> = cache.map { byId[id] }

    override fun getByTeamId(teamId: String): Result<List<Mentee>> = cache.map { byTeamId[teamId].orEmpty() }
}