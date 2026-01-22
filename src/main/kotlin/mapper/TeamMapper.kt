package mapper

import dataRaw.teamRaw
import domain.model.Team

class TeamMapper: Mapper<teamRaw,Team> {
    override fun toDomain(rawList: List<teamRaw>): List<Team> {
        return rawList.map { Raw ->
            Team(
                id = Raw.id,
                name = Raw.name,
                mentorLead = Raw.mentorLead
            )
        }}}
