package Data.Classes.Files

import java.io.File

// Function to read mentees.csv and convert each line into a MenteeRaw object
fun parseMenteData(): List<MenteeRaw> {
    val lines1 = File("src/main/resources/mentees.csv").readLines()
    return lines1.drop(1).map { line ->
        val parts = line.split(",")
        MenteeRaw(parts[0].trim(), parts[1].trim(), parts[2].trim())
    }
}

// Function to read performance.csv and convert each line into a PerformanceRaw object
fun parsePreformanceData(): List<PerformanceRaw> {
    val lines2 = File("src/main/resources/performance.csv").readLines()
    return lines2.drop(1).map { line2 ->
        val parts2 = line2.split(",")
        PerformanceRaw(parts2[0].trim(), parts2[1].trim(), parts2[2].trim(), parts2[3].trim())
    }
}

// Function to read teams.csv and convert each line into a TeamRaw object
fun parseTeamData(): List<TeamRaw> {
    return File("src/main/resources/teams.csv")
        .readLines()
        .drop(1)
        .map { it.split(",") }
        .map { TeamRaw(it[0], it[1], it[2]) }
}
