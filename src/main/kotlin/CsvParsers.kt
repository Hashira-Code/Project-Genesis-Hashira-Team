package models

import java.io.File

private val cachedMenteeLines = File("src/main/resources/mentees.csv").readLines()
private val cachedPerformanceLines = File("src/main/resources/performance.csv").readLines()
private val cachedTeamLines = File("src/main/resources/teams.csv").readLines()

fun parseMenteeData(): List<MenteeRaw> {
    return cachedMenteeLines
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
    return cachedPerformanceLines
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
    return cachedTeamLines
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

