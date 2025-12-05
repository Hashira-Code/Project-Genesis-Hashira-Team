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
                team?.mentees?.add(mentee)
                mentee
            }

    }




    fun buildSubmissions(rawSubmissions: List<PerformanceRaw>, mentees: List<Mentee>) {
        val menteesById = mentees.associateBy { it.menteeId }
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
