import Data.Classes.Files.parseMenteData
import Data.Classes.Files.parsePreformanceData

fun main(){
    val mentee = parseMenteData()
    println(mentee)


    val performance = parsePreformanceData()
    println(performance)
}


