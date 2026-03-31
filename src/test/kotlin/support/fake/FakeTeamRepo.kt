package support.fake

import domain.model.entity.Team
import domain.repository.TeamRepo

class FakeTeamRepo(
    private val teams: List<Team> = emptyList()
) : TeamRepo {

    override fun getAll(): Result<List<Team>> {
        return Result.success(teams)
    }

    override fun getById(id: String): Result<Team?> {
        return Result.success(teams.find { it.id == id })
    }
}