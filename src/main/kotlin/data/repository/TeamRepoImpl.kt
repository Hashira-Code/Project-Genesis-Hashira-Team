package data.repository

import data.mapper.Mapper
import domain.model.entity.Team
import data.model.TeamRaw
import data.dataSource.DataSource
import data.exception.mapCsvErrorToDomain
import domain.repository.TeamRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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

    suspend override fun getAll(): Result<List<Team>> = withContext(Dispatchers.IO) { cache }

    suspend override fun getById(id: String): Result<Team?> = withContext(Dispatchers.IO) { cache.map { byId[id] } }
}


