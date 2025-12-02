package models

import java.io.File

fun parseMenteeData(): List<MenteeRaw> {
    return File("src/main/resources/mentees.csv")
        .readLines()
        .drop(1)
        .map { line ->
            val fields = line.split(",")
            MenteeRaw(
                fields[0].trim(),
                fields[1].trim(),
                fields[2].trim()
            )
        }
}

fun parsePerformanceData(): List<PerformanceRaw> {
    return File("src/main/resources/performance.csv")
        .readLines()
        .drop(1)
        .map { line ->
            val fields = line.split(",")
            PerformanceRaw(
                fields[0].trim(),
                fields[1].trim(),
                fields[2].trim(),
                fields[3].trim()
            )
        }
}

fun parseTeamData(): List<TeamRaw> {
    return File("src/main/resources/teams.csv")
        .readLines()
        .drop(1)
        .map { line ->
            val fields = line.split(",")
            TeamRaw(
                fields[0].trim(),
                fields[1].trim(),
                fields[2].trim()
            )
        }
}