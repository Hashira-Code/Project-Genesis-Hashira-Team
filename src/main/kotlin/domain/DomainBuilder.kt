package domain

import models.TeamRaw
import models.MenteeRaw
import models.PerformanceRaw

class DomainBuilder {

    fun buildTeams(rawTeams: List<TeamRaw>): List<Team> {
        return rawTeams.map { rawTeam ->
            Team(
                teamId = rawTeam.teamId,
                teamName = rawTeam.teamName,
                mentorLead = rawTeam.mentorLead

            )
        }
    }




    fun buildMentees(rawMentees: List<MenteeRaw>, teams: List<Team>): List<Mentee> {
            val teamsById = teams.associateBy { it.teamId }
            return rawMentees.map { rawMentee ->
                val team = teamsById[rawMentee.teamId]

                val mentee = Mentee(
                    menteeId = rawMentee.menteeId,
                    name = rawMentee.name,
                    teamId = rawMentee.teamId
                )
                mentee.team = team
                mentee
            }

    }

















fun buildSubmissions(rawSubmissions: List<PerformanceRaw>, mentees: List<Mentee>): List<PerformanceSubmission> {
        return listOf()


}
}
