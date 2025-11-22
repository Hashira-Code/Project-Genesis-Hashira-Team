
import Data.Classes.Files.parseMenteData
import Data.Classes.Files.parsePreformanceData
import Data.Classes.Files.parseTeamData

fun main(){
    // Call the parser functions to read the CSV data
    val mentee = parseMenteData()
    // Print the number of items to make sure the parsing works
    println(mentee.size)

    val performance = parsePreformanceData()
    println(performance.size)

    val teams = parseTeamData()
    println(teams.size)


}


