package data.repository

import data.dataSource.DataSource
import domain.model.Project
import domain.repository.ProjectRepo
import data.mapper.Mapper
import data.model.ProjectRaw

class ProjectRepoImpl(
    private val dataSource: DataSource,
    private val mapper: Mapper<ProjectRaw, Project>
) : ProjectRepo {
    private val cache: Result<List<Project>> by lazy {
        runCatching { mapper.toDomain(dataSource.getAllProjects()) }
    }
    private val byTeamId: Map<String, List<Project>> by lazy {
        cache.getOrDefault(emptyList()).groupBy { it.teamId }
    }

    override fun getAll(): Result<List<Project>> = cache
    override fun getByTeamId(teamId: String): Result<List<Project>> = cache.map { byTeamId[teamId].orEmpty() }
}