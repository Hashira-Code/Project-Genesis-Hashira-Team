package data.fake

import domain.model.entity.Team
import domain.repository.TeamRepo

class FakeTeamRepo(
    private val teams: List<Team> = emptyList()
) : TeamRepo {

    override suspend fun getAll(): Result<List<Team>> {
        return Result.success(teams)
    }

    override suspend fun getById(id: String): Result<Team?> {
        return Result.success(teams.find { it.id == id })
    }
}