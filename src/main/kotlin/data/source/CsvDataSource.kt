package data.source

import data.source.DataSource
import data.model.AttendanceRaw
import data.model.MenteeRaw
import data.model.PerformanceRaw
import data.model.ProjectRaw
import data.model.TeamRaw
import java.io.File

class CsvDataSource(val path:String): DataSource {

    override fun getAllAttendance():List<AttendanceRaw>{
       return  attendanceParse()
    }

    override fun getAllPerformance(): List<PerformanceRaw> {
      return performanceParse()
    }
    override fun getAllMentees(): List<MenteeRaw> {
       return menteeParse()
    }

    override fun getAllTeams(): List<TeamRaw> {
        return teamParse()
    }

    override fun getAllProjects(): List<ProjectRaw> {
        return projectParse()
    }


    private fun menteeParse():List<MenteeRaw>{
         return  readLinesCsv(path).map{raw ->
             MenteeRaw(
                 id = raw[0].trim(),
                 name = raw[1].trim(),
                 teamId = raw[2].trim()
             )
         } }

    private fun teamParse():List<TeamRaw>{
        return readLinesCsv(path).map{raw ->
            TeamRaw(
                id = raw[0].trim(),
                name = raw[1].trim(),
                mentorLead = raw[2].trim()
            )
        } }

    private fun projectParse():List<ProjectRaw>{
        return  readLinesCsv(path).map{raw ->
            ProjectRaw(
                id = raw[0].trim(),
                name = raw[1].trim(),
                teamId = raw[2].trim()
            )
        } }
    private fun performanceParse(): List<PerformanceRaw> {
        return readLinesCsv("performance.csv").mapNotNull { raw ->
            PerformanceRaw(
                raw[1],
                raw[2],
                raw[3].toDoubleOrNull() ?: 0.0,
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