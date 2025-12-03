package domain

import models.TeamRaw
import models.MenteeRaw
import models.PerformanceRaw

class DomainBuilder {
    fun buildTeams(rawTeams: List<TeamRaw>): List<Team> {
        // Use the 'map' function to transform the list of TeamRaw into a list of Team
        return rawTeams.map { rawTeam ->
            // For each rawTeam, we will create a new Team object
            Team(
                // Here we must define the properties of the Team class
                id = rawTeam.id,       // Example: Extract the ID from the raw data
                name = rawTeam.name,   // Example: Extract the Name from the raw data
                mentees = emptyList()  // We will temporarily leave this list empty
            )
        }
    }





    fun buildMentees(rawMentees: List<MenteeRaw>, teams: List<Team>): List<Mentee> {
        return listOf()
    }







    fun buildSubmissions(rawSubmissions: List<PerformanceRaw>, mentees: List<Mentee>): List<PerformanceSubmission> {
        return listOf()
    }
}
