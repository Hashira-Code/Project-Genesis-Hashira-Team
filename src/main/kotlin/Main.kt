
import Data.Classes.Files.parseMenteData
import Data.Classes.Files.parsePreformanceData
import Data.Classes.Files.parseTeamData

fun main(){
    val mentee = parseMenteData()
    println(mentee.size)


    val performance = parsePreformanceData()
    println(performance.size)

    val teams = parseTeamData()
    println(teams.size)

}


