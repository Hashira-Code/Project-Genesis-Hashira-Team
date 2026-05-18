package data.dataSource

import data.model.AttendanceRaw
import data.model.MenteeRaw
import data.model.PerformanceRaw
import data.model.ProjectRaw
import data.model.TeamRaw

class CsvDataSource(
    private val fileReader: CsvFileReader,
    private val rowParser: CsvRowParser
) : DataSource {

    override suspend fun getAllMentees(): List<MenteeRaw> =
        parseResource(MENTEES_FILE, MENTEE_COLUMNS) { row ->
            MenteeRaw(
                id = row[0],
                name = row[1],
                teamId = row[2])
        }

    override suspend fun getAllTeams(): List<TeamRaw> =
        parseResource(TEAMS_FILE, TEAM_COLUMNS) { row ->
            TeamRaw(
                id = row[0],
                name = row[1],
                mentorLead = row[2])
        }

    override suspend fun getAllProjects(): List<ProjectRaw> =
        parseResource(PROJECTS_FILE, PROJECT_COLUMNS) { row ->
            ProjectRaw(
                id = row[0],
                name = row[1],
                teamId = row[2])
        }

    override suspend fun getAllPerformance(): List<PerformanceRaw> =
        parseResource(PERFORMANCE_FILE, PERFORMANCE_COLUMNS) { row ->
            PerformanceRaw(
                menteeId = row[0],
                id = row[1],
                type = row[2],
                score = row[3].toDoubleOrNull() ?: 0.0)
        }

    override suspend fun getAllAttendance(): List<AttendanceRaw> =
        parseResource(ATTENDANCE_FILE, ATTENDANCE_MIN_COLUMNS) { row ->
            AttendanceRaw(
                menteeId = row[0],
                weeks = row.drop(1))
        }

    private suspend fun <T> parseResource(
        resource: String,
        expectedColumns: Int,
        mapper: (List<String>) -> T
    ): List<T> {
        val rows = fileReader.readLines(resource)
        return rowParser.parseRows(rows, resource, expectedColumns, mapper)
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
