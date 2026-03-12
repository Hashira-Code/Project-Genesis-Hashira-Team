package data.mapper

import data.model.TeamRaw
import domain.model.entity.Team

class TeamMapper : Mapper<TeamRaw, Team> {
    override fun toDomain(rawList: List<TeamRaw>): List<Team> {
        return rawList.map { raw ->
            Team.create(
                id = raw.id,
                name = raw.name,
                mentorLead = raw.mentorLead
            )
        }
    }
}

