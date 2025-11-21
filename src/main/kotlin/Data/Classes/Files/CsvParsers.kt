package Data.Classes.Files
import java.io.File
fun parseMenteData(): List<MenteeRaw> {
    val lines = File("src/main/resources/mentees.csv").readLines()
    return lines.drop(1).map{line->
        val parts = line.split(",")
        MenteeRaw(parts[0].trim(),parts[1].trim(),parts[2].trim())
    }
}


