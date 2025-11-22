import Data.Classes.Files.parseMenteData
import Data.Classes.Files.parsePreformanceData
import Data.Classes.Files.TeamRaw

fun main(){
    val mentee = parseMenteData()
    println(mentee)


    val performance = parsePreformanceData()
    println(performance)
}


