package data.repository
import data.mapper.Mapper
import domain.model.Team
import data.model.TeamRaw
import data.source.DataSource
import domain.repository.TeamRepo


class DefaultTeamRepository(
    private val dataSource: DataSource,
    private val mapper: Mapper<TeamRaw, Team>
) : TeamRepo {

    private val cache: List<Team> by lazy {
        mapper.toDomain(dataSource.getAllTeams())
    }


    override fun getAll(): List<Team> = cache


}