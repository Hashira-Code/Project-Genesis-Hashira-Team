
import models.parseMenteeData
import models.parsePerformanceData
import models.parseTeamData

fun main(){
    val mentee = parseMenteeData()
    println(mentee.size)

    val performance = parsePerformanceData()
    println(performance.size)

    val teams = parseTeamData()
    println(teams.size)


}


