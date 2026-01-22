package data.repository
import data.source.DataSource
import domain.model.Project
import domain.repository.ProjectRepo
import data.mapper.Mapper
import data.model.ProjectRaw

class ProjectRepoImpl(
    private val dataSource: DataSource,
    private val mapper: Mapper<ProjectRaw, Project>
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