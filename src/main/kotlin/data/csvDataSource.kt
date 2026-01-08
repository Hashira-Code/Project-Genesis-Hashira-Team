package data

import data.model.*
import dataRaw.menteeRaw
import dataRaw.projectRaw
import dataRaw.teamRaw
import java.io.File

class csvDataSource(val path: String) : dataSource {


    override fun getAllMentees(): List<menteeRaw> {
        return menteeParse()
    }

    override fun getAllTeams(): List<teamRaw> {
        return teamParse()
    }

    override fun getAllProjects(): List<projectRaw> {
        return projectParse()
    }

    override fun getAllPerformance(): List<PerformanceRaw> {
        return performanceParse()
    }

    override fun getAllAttendance(): List<AttendanceRaw> {
        return attendanceParse()
    }


    private fun menteeParse(): List<menteeRaw> {
        return readLinesCsv("mentee.csv").mapNotNull { raw ->
            menteeRaw(
                id = raw[0],
                name = raw[1],
                teamId = raw[2]
            )
        }
    }

    private fun teamParse(): List<teamRaw> {
        return readLinesCsv("team.csv").mapNotNull { raw ->
            teamRaw(
                id = raw[0],
                name = raw[1],
                mentorLead = raw[2]
            )
        }
    }

    private fun projectParse(): List<projectRaw> {
        return readLinesCsv("project.csv").mapNotNull { raw ->
            projectRaw(

                id = raw[0],
                name = raw[1],
                teamId = raw[2]
            )
        }
    }

    private fun performanceParse(): List<PerformanceRaw> {
        return readLinesCsv("performance.csv").mapNotNull { raw ->
            PerformanceRaw(
                raw[1],
                raw[2],
                raw[3].toDouble(),
                raw[0]


            )
        }
    }

    private fun attendanceParse(): List<AttendanceRaw> {
        return readLinesCsv("attendance.csv").mapNotNull { raw ->
            AttendanceRaw(
                raw[0],
                raw.drop(1)
            )
        }
    }

    private fun readLinesCsv(resource: String): List<List<String>> {
        return File("$path/$resource").readLines()
            .drop(1)
            .map { line ->
                line.split(",").map { feild -> feild.trim() }

            }


    }

}


