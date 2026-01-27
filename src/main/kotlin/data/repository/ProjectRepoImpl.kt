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


    override fun getAll(): List<Project> = cache

}