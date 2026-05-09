package data.fake

import domain.model.entity.Project
import domain.repository.ProjectRepo

class FakeProjectRepo(
    private val projects: List<Project> = emptyList()
) : ProjectRepo {

    override suspend fun getAll(): Result<List<Project>> {
        return Result.success(projects)
    }

    override suspend fun getByTeamId(teamId: String): Result<List<Project>> {
        return Result.success(projects.filter { it.teamId == teamId })
    }
}