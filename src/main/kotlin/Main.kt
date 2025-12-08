import models.parseTeamData
import models.parseMenteeData
import models.parsePerformanceData
import domain.DomainBuilder

fun main(){

    val rawTeams = parseTeamData()
    val rawMentees = parseMenteeData()
    val rawSubmissions = parsePerformanceData()

    val domainBuilder = DomainBuilder()
    val teams = domainBuilder.buildDomain(
        rawTeams,
        rawMentees,
        rawSubmissions
    )


    val firstTeam = teams.firstOrNull()

    if (firstTeam != null) {
        println("Team: ${firstTeam.teamName}")
        println("Mentor Lead: ${firstTeam.mentorLead}")
        println("Mentees:")


        firstTeam.mentees.forEach { mentee ->
            println(" - ${mentee.name} (ID: ${mentee.Id})")
        }
    } else {
        println("No Teams")
    }
}




