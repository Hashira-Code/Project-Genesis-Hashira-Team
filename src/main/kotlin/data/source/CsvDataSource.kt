package data.source
import data.validation.Validator
import data.source.DataSource
import data.model.AttendanceRaw
import data.model.MenteeRaw
import data.model.PerformanceRaw
import data.model.ProjectRaw
import data.model.TeamRaw
import java.io.File
import kotlin.io.readLines

class CsvDataSource(
    val path:String,
    private val fileValidator: Validator<File>
   ): DataSource {

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
         return  readLinesCsv(MENTEES_FILE).map{raw ->
             MenteeRaw(
                 id = raw[0],
                 name = raw[1],
                 teamId = raw[2]
             )
         }
    }
    private fun teamParse():List<TeamRaw>{
        return readLinesCsv(TEAMS_FILE).map{raw ->
            TeamRaw(
                id = raw[0],
                name = raw[1],
                mentorLead = raw[2]
            )
        }
    }
    private fun projectParse():List<ProjectRaw>{
        return  readLinesCsv(PROJECTS_FILE).map{raw ->
            ProjectRaw(
                id = raw[0],
                name = raw[1],
                teamId = raw[2]
            )
        }
    }
    private fun performanceParse(): List<PerformanceRaw> {
        return readLinesCsv(PERFORMANCE_FILE).map { raw ->
            PerformanceRaw(
                id = raw[1],
                type = raw[2],
                score = raw[3].toDoubleOrNull() ?: 0.0,
                menteeId = raw[0]
            )
        }
    }
    private fun attendanceParse(): List<AttendanceRaw> {
        return readLinesCsv(ATTENDANCE_FILE).map { raw ->
            AttendanceRaw(
                menteeId =  raw[0],
                weeks = raw.drop(1)
            )

        }
    }
    fun readLinesCsv(resource: String): List<List<String>> {
        val file = File("$path/$resource")
        fileValidator.validate(file).getOrThrow()
        return file.readLines()
            .drop(1)
            .map { line ->
                line.split(",").map { it.trim() }
            }
    }
    companion object {
        private const val ATTENDANCE_FILE="attendance.csv"
        private const val PERFORMANCE_FILE="performance.csv"
        private const val TEAMS_FILE="teams.csv"
        private const val MENTEES_FILE="mentees.csv"
        private const val PROJECTS_FILE="projects.csv"

         }
    }