package data.dataSource

import data.model.*
import data.validation.EmptyFieldValidator
import data.validation.MissingColumnsValidator
import data.validation.Validator
import java.io.File

class CsvDataSource(
    private val path: String,
    private val fileValidator: Validator<File>,
    private val lineValidator: Validator<String>
) : DataSource {


    private val emptyFieldValidator = EmptyFieldValidator()

    override fun getAllAttendance() = attendanceParse()
    override fun getAllPerformance() = performanceParse()
    override fun getAllMentees() = menteeParse()
    override fun getAllTeams() = teamParse()
    override fun getAllProjects() = projectParse()


    private fun menteeParse(): List<MenteeRaw> =
        parseFile(
            MENTEES_FILE,
            MENTEE_COLUMNS,
        ) { raw ->
            MenteeRaw(
                id = raw[0],
                name = raw[1],
                teamId = raw[2]
            )
        }

    private fun teamParse(): List<TeamRaw> =
        parseFile(
            TEAMS_FILE,
            TEAM_COLUMNS,
        ) { raw ->
            TeamRaw(
                id = raw[0],
                name = raw[1],
                mentorLead = raw[2]
            )
        }

    private fun projectParse(): List<ProjectRaw> =
        parseFile(
            PROJECTS_FILE,
            PROJECT_COLUMNS,
        ) { raw ->
            ProjectRaw(
                id = raw[0],
                name = raw[1],
                teamId = raw[2]
            )
        }

    private fun performanceParse(): List<PerformanceRaw> =
        parseFile(
            PERFORMANCE_FILE,
            PERFORMANCE_COLUMNS,
        ) { raw ->
            PerformanceRaw(
                id = raw[1],
                type = raw[2],
                score = raw[3].toDoubleOrNull() ?: 0.0,
                menteeId = raw[0]
            )
        }

    private fun attendanceParse(): List<AttendanceRaw> =
        parseFile(
            ATTENDANCE_FILE,
            ATTENDANCE_MIN_COLUMNS
        ) { raw ->
            AttendanceRaw(
                menteeId = raw[0],
                weeks = raw.drop(1)
            )
        }

    private fun <T> parseFile(
        resource: String,
        expectedColumns: Int,
        mapper: (List<String>) -> T
    ): List<T> {
        val columnsValidator = MissingColumnsValidator(expectedColumns)
        return readLinesCsv(resource).map { raw ->
            columnsValidator.validate(raw).getOrThrow()
            emptyFieldValidator.validate(raw).getOrThrow()
            mapper(raw)
        }
    }

    private fun readLinesCsv(resource: String): List<List<String>> {
        val file = File("$path/$resource")
        fileValidator.validate(file).getOrThrow()

        return file.readLines()
            .drop(1)
            .filter { line ->
                lineValidator.validate(line).isSuccess
            }
            .map { line ->
                line.split(",").map { it.trim() }
            }
    }

    companion object {
        private const val ATTENDANCE_FILE = "attendance.csv"
        private const val PERFORMANCE_FILE = "performance.csv"
        private const val TEAMS_FILE = "teams.csv"
        private const val MENTEES_FILE = "mentees.csv"
        private const val PROJECTS_FILE = "projects.csv"
        private const val MENTEE_COLUMNS = 3
        private const val TEAM_COLUMNS = 3
        private const val PROJECT_COLUMNS = 3
        private const val PERFORMANCE_COLUMNS = 4
        private const val ATTENDANCE_MIN_COLUMNS = 2
    }
}
