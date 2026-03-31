package support.fake

import domain.model.entity.Project
import domain.repository.ProjectRepo

class FakeProjectRepo(
    private val projects: List<Project> = emptyList()
) : ProjectRepo {

    override fun getAll(): Result<List<Project>> {
        return Result.success(projects)
    }

    override fun getByTeamId(teamId: String): Result<List<Project>> {
        return Result.success(projects.filter { it.teamId == teamId })
    }
}