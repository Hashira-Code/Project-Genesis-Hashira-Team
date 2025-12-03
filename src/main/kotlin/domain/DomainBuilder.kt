package domain

import models.TeamRaw
import models.MenteeRaw
import models.PerformanceRaw

class DomainBuilder {

    fun buildTeams(rawTeams: List<TeamRaw>): List<Team> {
        return listOf()
    }





    fun buildMentees(rawMentees: List<MenteeRaw>, teams: List<Team>): List<Mentee> {
        return listOf()
    }







    fun buildSubmissions(rawSubmissions: List<PerformanceRaw>, mentees: List<Mentee>): List<PerformanceSubmission> {
        return listOf()
    }
}
