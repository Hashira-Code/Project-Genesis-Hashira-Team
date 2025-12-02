package models

import java.io.File


private var cachedMentees: List<MenteeRaw>? = null

fun parseMenteeData(): List<MenteeRaw> {
    if (cachedMentees == null) {
        cachedMentees = File("src/main/resources/mentees.csv")
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
    return cachedMentees!!
}


private var cachedPerformances: List<PerformanceRaw>? = null

fun parsePerformanceData(): List<PerformanceRaw> {
    if (cachedPerformances == null) {
        cachedPerformances = File("src/main/resources/performance.csv")
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
    return cachedPerformances!!
}


private var cachedTeams: List<TeamRaw>? = null

fun parseTeamData(): List<TeamRaw> {
    if (cachedTeams == null) {
        cachedTeams = File("src/main/resources/teams.csv")
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
    return cachedTeams!!
}
