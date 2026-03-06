package data.repository

import data.mapper.Mapper
import domain.model.Team
import data.model.TeamRaw
import data.dataSource.DataSource
import data.exception.mapCsvErrorToDomain
import domain.repository.TeamRepo


class TeamRepoImpl(
    private val dataSource: DataSource,
    private val mapper: Mapper<TeamRaw, Team>
) : TeamRepo {

    private val cache: Result<List<Team>> by lazy {
        runCatching { mapper.toDomain(dataSource.getAllTeams()) }
            .mapCsvErrorToDomain()
    }

    private val byId: Map<String, Team> by lazy {
        cache.getOrThrow().associateBy { it.id }
    }

    override fun getAll(): Result<List<Team>> = cache

    override fun getById(id: String): Result<Team?> = cache.map { byId[id] }
}
