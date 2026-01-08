package mapper

import dataRaw.teamRaw
import domain.model.Team

class TeamMapper: Mapper<teamRaw,Team> {
    override fun toDaomain(rawList: List<teamRaw>): List<Team> {
        return rawList.map { Raw ->
            Team(
                id = Raw.id,
                name = Raw.name,
                mentorLead = Raw.mentorLead
            )
        }}}