package domain

import models.TeamRaw
import models.MenteeRaw
import models.PerformanceRaw

class DomainBuilder {

    private fun buildTeams(rawTeams: List<TeamRaw>): List<Team> {
        return rawTeams.map { raw->
            Team(
                id = raw.teamId,
                Name = raw.teamName,
                mentorLead = raw.mentorLead
            )
        }
    }
   private fun buildMentees(rawMentees: List<MenteeRaw>, teams: List<Team>): List<Mentee> {
        val teamsById = getTeamsById(teams)
        return rawMentees.map { raw ->
            val team = getTeamForRaw(raw, teamsById)
            val mentee = createMenteeFromRaw(raw)
            setTeamAndAddMentee(mentee, team)
            mentee
        }
    }
    private fun getTeamsById(teams: List<Team>) =
        teams.associateBy { it.id }
    private fun getTeamForRaw(raw: MenteeRaw, teamsById: Map<String, Team>): Team? {
            return teamsById[raw.menteeId]
    }
    private fun createMenteeFromRaw(raw: MenteeRaw): Mentee {
            return Mentee(
                id = raw.menteeId,
                name = raw.name,
                teamId = raw.teamId
            )
    }
    private fun setTeamAndAddMentee(mentee: Mentee, team: Team?) {
            mentee.team = team
            team?.mentees?.add(mentee)
    }
    private fun buildSubmissions(rawSubmissions: List<PerformanceRaw>, mentees: List<Mentee>) {
        val menteesById = mentees.associateBy { it.id }
        rawSubmissions.forEach { raw ->
            val submission = PerformanceSubmission(
                menteeId = raw.menteeId,
                id = raw.submissionId,
                type = raw.submissionType ,
                score = raw.score
            )
        }
    }
    fun buildDomain(
        rawTeams: List<TeamRaw>,
        rawMentees: List<MenteeRaw>,
        rawSubmissions: List<PerformanceRaw>
    ): List<Team> {
        val teams = buildTeams(rawTeams)
        val mentees = buildMentees(rawMentees, teams)
        buildSubmissions(rawSubmissions, mentees)
        return teams
    }
}
