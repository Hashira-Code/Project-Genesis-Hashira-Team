package data

import dataRaw.AttendanceRaw
import dataRaw.PerformanceRaw
import dataRaw.menteeRaw
import dataRaw.projectRaw
import dataRaw.teamRaw
import java.io.File

class csvDataSource(val path:String): dataSource {

    override fun getAllAttendance():List<AttendanceRaw>{
       return  attendanceParse()
    }

    override fun getAllPerformance(): List<PerformanceRaw> {
      return performanceParse()
    }
    override fun getAllMentees(): List<menteeRaw> {
       return menteeParse()
    }

    override fun getAllTeams(): List<teamRaw> {
        return teamParse()
    }

    override fun getAllProjects(): List<projectRaw> {
        return projectParse()
    }


    private fun menteeParse():List<menteeRaw>{
         return  readLinesCsv(path).map{Raw ->
             menteeRaw(
                 id=Raw[0].trim(),
                 name=Raw[1].trim(),
                 teamId=Raw[2].trim()
             )
         } }

    private fun teamParse():List<teamRaw>{
        return readLinesCsv(path).map{Raw ->
            teamRaw(
                id=Raw[0].trim(),
                name=Raw[1].trim(),
                mentorLead = Raw[2].trim()
            )
        } }

    private fun projectParse():List<projectRaw>{
        return  readLinesCsv(path).map{Raw ->
            projectRaw(
                id=Raw[0].trim(),
                name=Raw[1].trim(),
                teamId=Raw[2].trim()
            )
        } }
    private fun performanceParse(): List<PerformanceRaw> {
        return readLinesCsv("performance.csv").mapNotNull { raw ->
            PerformanceRaw(
                raw[1],
                raw[2],
                raw[3].toDoubleOrNull() ?:0.0,
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

    fun readLinesCsv(resource: String): List<List<String>> {
        return File("$path/$resource").readLines()
            .drop(1)
            .map { line ->
                line.split(",").map { feild -> feild.trim() }
            }


    }




    }