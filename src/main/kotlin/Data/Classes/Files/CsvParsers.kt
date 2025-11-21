package Data.Classes.Files
import java.io.File
fun parseMenteData(): List<MenteeRaw> {
    val lines1 = File("src/main/resources/mentees.csv").readLines()
    return lines1.drop(1).map{line->
        val parts = line.split(",")
        MenteeRaw(parts[0].trim(),parts[1].trim(),parts[2].trim())
    }
}
fun parsePreformanceData(): List<PerformanceRaw>{
    val lines2 = File("src/main/resources/performance.csv").readLines()
    return lines2.drop(1).map { line2->
        val parts2 = line2.split(",")
        PerformanceRaw(parts2[0].trim(),parts2[1].trim(),parts2[2].trim(),parts2[3].trim())
    }


}

