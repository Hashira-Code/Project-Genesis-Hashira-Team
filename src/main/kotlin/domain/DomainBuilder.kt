package domain
import models.TeamRaw
import models.MenteeRaw
import models.PerformanceRaw
class DomainBuilder {
    fun buildDomain(rawTeams: List<TeamRaw>, rawMentees: List<MenteeRaw>, rawSubmissions: List<PerformanceRaw>): List<Team> {
        val teams = buildTeams(rawTeams)
        val mentees = buildMentees(rawMentees, teams)
        buildSubmissions(rawSubmissions, mentees)
        return teams
        }
     }
     private fun buildTeams(rawTeams: List<TeamRaw>): List<Team> {
        return rawTeams.map { raw ->
            createTeamFromRaw(raw)
        }
    }
    private fun createTeamFromRaw(raw: TeamRaw): Team {
        return Team(
            id = raw.teamId,
            name = raw.teamName,
            mentorLead = raw.mentorLead
        )
    }
    private fun getTeamsById(teams: List<Team>): Map<String, Team> =
        teams.associateBy { it.id }
    private fun buildMentees(rawMentees: List<MenteeRaw>, teams: List<Team>): List<Mentee> {
        val teamsById = getTeamsById(teams)
        return rawMentees.map { raw ->
            val mentee = createMenteeFromRaw(raw)
            val team = getTeamForRaw(raw, teamsById)
            setTeamAndAddMentee(mentee, team)
            mentee
        }
    }
    private fun getTeamForRaw(raw: MenteeRaw, teamsById: Map<String, Team>): Team? {
        return teamsById[raw.teamId]
    }
    private fun createMenteeFromRaw(raw: MenteeRaw): Mentee {
        return Mentee(
            id = raw.menteeId,
            name = raw.name,
            teamId = raw.teamId
        )
    }
    private fun setTeamAndAddMentee(
        mentee: Mentee,
        team: Team?
    ) {
        mentee.team = team
        team?.mentees?.add(mentee)
    }
    private fun buildSubmissions(rawSubmissions: List<PerformanceRaw>, mentees: List<Mentee>)
    {
        val menteesById = getMenteesById(mentees)
        rawSubmissions.forEach { raw ->
            val submission = createSubmissionFromRaw(raw)
            val mentee = menteesById[raw.menteeId]
            mentee?.submissions?.add(submission)
        }
    }
    private fun getMenteesById(mentees: List<Mentee>): Map<String, Mentee> =
        mentees.associateBy { it.id }
    private fun createSubmissionFromRaw(raw: PerformanceRaw): PerformanceSubmission {
        return PerformanceSubmission(
            id = raw.submissionId,
            type = raw.submissionType,
            score = raw.score,
            menteeId = raw.menteeId
        )
    }



