package data.repository
import data.dataSource
import domain.model.Project
import domain.repository.ProjectRepo
import mapper.Mapper
import dataRaw.projectRaw

class ProjectRepoImpl(
    private val dataSource: dataSource,
    private val mapper: Mapper<projectRaw, Project>
) : ProjectRepo {
    private val cache: List<Project> by lazy {
        mapper.toDomain(dataSource.getAllProjects())
    }
    private val byId: Map<String, Project> by lazy {
        cache.associateBy { it.id }
    }
    private val byTeamId: Map<String, List<Project>> by lazy {
        cache.groupBy { it.teamId }
    }
    override fun getAll(): List<Project> = cache
    override fun getByTeamId(teamId: String): List<Project> = byTeamId[teamId].orEmpty()
}