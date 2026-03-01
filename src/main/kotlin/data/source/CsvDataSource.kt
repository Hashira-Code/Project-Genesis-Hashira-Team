package data.source

import data.validation.Validator
import data.model.AttendanceRaw
import data.model.MenteeRaw
import data.model.PerformanceRaw
import data.model.ProjectRaw
import data.model.TeamRaw
import java.io.File
import data.validation.LineIsNotEmptyValidator
import data.validation.MissingColumnsValidator
import data.validation.UniqueValueValidator
import data.validation.EmptyFieldValidator

class CsvDataSource(
    val path: String,
    private val fileValidator: Validator<File>
) : DataSource {

    private val menteeIdValidator = UniqueValueValidator<String>()
    private val teamIdValidator = UniqueValueValidator<String>()
    private val projectIdValidator = UniqueValueValidator<String>()
    private val performanceIdValidator = UniqueValueValidator<String>()

    private val threeColumnsValidator = MissingColumnsValidator(3)
    private val fourColumnsValidator = MissingColumnsValidator(4)
    private val twoColumnsValidator = MissingColumnsValidator(2)

    private val emptyFieldValidator = EmptyFieldValidator()

    override fun getAllAttendance() = attendanceParse()
    override fun getAllPerformance() = performanceParse()
    override fun getAllMentees() = menteeParse()
    override fun getAllTeams() = teamParse()
    override fun getAllProjects() = projectParse()

    private fun <T> parseFile(
        resource: String,
        columnsValidator: MissingColumnsValidator,
        idValidator: UniqueValueValidator<String>?,
        mapper: (List<String>) -> T
    ): List<T> {

        return readLinesCsv(resource).map { raw ->
            columnsValidator.validate(raw).getOrThrow()
            emptyFieldValidator.validate(raw).getOrThrow()
            idValidator?.validate(raw[0])?.getOrThrow()
            mapper(raw)
        }
    }

    private fun menteeParse(): List<MenteeRaw> =
        parseFile(
            MENTEES_FILE,
            threeColumnsValidator,
            menteeIdValidator
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
            threeColumnsValidator,
            teamIdValidator
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
            threeColumnsValidator,
            projectIdValidator
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
            fourColumnsValidator,
            performanceIdValidator
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
            twoColumnsValidator,
            null
        ) { raw ->
            AttendanceRaw(
                menteeId = raw[0],
                weeks = raw.drop(1)
            )
        }

    fun readLinesCsv(resource: String): List<List<String>> {
        val file = File("$path/$resource")
        fileValidator.validate(file).getOrThrow()

        val lineValidator = LineIsNotEmptyValidator()

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
    }
}