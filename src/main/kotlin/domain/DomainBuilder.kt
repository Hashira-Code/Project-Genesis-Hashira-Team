package domain

import models.TeamRaw
import models.MenteeRaw
import models.PerformanceRaw

class DomainBuilder {

    private fun buildTeams(rawTeams: List<TeamRaw>): List<Team> {
        return rawTeams.map { rawTeam ->
            Team(
                Id = rawTeam.Id,
                teamName = rawTeam.teamName,
                mentorLead = rawTeam.mentorLead,
                mentees = mutableListOf()

            )
        }
    }
    private fun buildMentees(rawMentees: List<MenteeRaw>, teams: List<Team>): List<Mentee> {
        val teamsById = teams.associateBy { it.Id }
        val mentees = mutableListOf<Mentee>()
        rawMentees.forEach { raw ->
            val mentee = Mentee(
                Id = raw.Id,
                name = raw.name,
                teamId = raw.teamId
            )
            val team = teamsById[raw.teamId]
            mentee.assignTeam(team)
            team?.addMentee(mentee)
            mentees.add(mentee)
        }
        return mentees
    }
    private fun buildSubmissions(rawSubmissions: List<PerformanceRaw>, mentees: List<Mentee>) {
        val menteesById = mentees.associateBy { it.Id }
        rawSubmissions.forEach { raw ->
            val submission = PerformanceSubmission(
                menteeId = raw.menteeId,
                submissionId = raw.submissionId,
                submissionType = raw.submissionType,
                score = raw.score
            )
            menteesById[raw.menteeId]?.addSubmission(submission)
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
