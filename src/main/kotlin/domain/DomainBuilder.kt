package domain

import models.TeamRaw
import models.MenteeRaw
import models.PerformanceRaw

class DomainBuilder {

    private fun buildTeams(rawTeams: List<TeamRaw>): List<Team> {
        return rawTeams.map { rawTeam ->
            Team(
                id = rawTeam.id,
                teamName = rawTeam.teamName,
                mentorLead = rawTeam.mentorLead,
                mentees = mutableListOf()

            )
        }
    }

    private fun buildMentees(rawMentees: List<MenteeRaw>, teams: List<Team>): List<Mentee> {
            val teamsById = teams.associateBy { it.id }
            return rawMentees.map { raw ->
                val team = teamsById[raw.id]
                val mentee = Mentee(
                    id = raw.id,
                    name = raw.name,
                    teamId = raw.teamId
                )
                mentee.team = team
                team?.mentees?.add(mentee)
                mentee
            }
    }

    private fun buildSubmissions(rawSubmissions: List<PerformanceRaw>, mentees: List<Mentee>) {
        val menteesById = mentees.associateBy { it.id }
        rawSubmissions.forEach { raw ->
            val submission = PerformanceSubmission(
                menteeId = raw.menteeId,
                submissionId = raw.submissionId,
                submissionType = raw.submissionType,
                score = raw.score
            )
            menteesById[raw.menteeId]?.submissions?.add(submission)
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
