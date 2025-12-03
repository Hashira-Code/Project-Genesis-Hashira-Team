package Data.Classes.Files

import java.io.File


fun parseMenteData(): List<MenteeRaw> {
    return File("src/main/resources/mentees.csv")
        .readLines()
        .drop(1)
        .map { line ->
            val fields=line.split(",")
            MenteeRaw(
                fields[0].trim()
            )

        }

}


fun parsePreformanceData(): List<PerformanceRaw> {
    val lines2 = File("src/main/resources/performance.csv").readLines()
    return lines2.drop(1).map { line2 ->
        val parts2 = line2.split(",")
        PerformanceRaw(parts2[0].trim(), parts2[1].trim(), parts2[2].trim(), parts2[3].trim())
    }
}




fun parseTeamData(): List<TeamRaw> {
    return File("src/main/resources/teams.csv")
        .readLines()
        .drop(1)
        .map { it.split(",") }
        .map { TeamRaw(it[0], it[1], it[2]) }
}
