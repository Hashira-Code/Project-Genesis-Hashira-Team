import data.dataSource.CsvDataSource
import data.dataSource.DataSource
import data.mapper.AttendanceMapper
import data.mapper.Mapper
import data.mapper.MenteeMapper
import data.mapper.PerformanceMapper
import data.mapper.ProjectMapper
import data.mapper.TeamMapper
import data.model.AttendanceRaw
import data.model.MenteeRaw
import data.model.PerformanceRaw
import data.model.ProjectRaw
import data.model.TeamRaw
import data.repository.AttendanceRepoImpl
import data.repository.MenteeRepoImpl
import data.repository.PerformanceRepoImpl
import data.repository.ProjectRepoImpl
import data.repository.TeamRepoImpl
import data.validation.EmptyFieldValidator
import data.validation.FileValidator
import data.validation.LineIsNotEmptyValidator
import data.validation.Validator
import domain.model.entity.Attendance
import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.Project
import domain.model.entity.Team
import domain.repository.AttendanceRepo
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import domain.repository.ProjectRepo
import domain.repository.TeamRepo
import domain.validation.ScoreValidator
import domain.validation.WeekNumberValidator
import java.io.File

private object AppContainer {
    private const val RESOURCES_PATH = "src/main/resources"

    val fileValidator: Validator<File> = FileValidator()
    val lineValidator: Validator<String> = LineIsNotEmptyValidator()
    val emptyFieldValidator: Validator<List<String>> = EmptyFieldValidator()

    val weekNumberValidator = WeekNumberValidator()
    val scoreValidator = ScoreValidator()

    val dataSource: DataSource = CsvDataSource(
        path = RESOURCES_PATH,
        fileValidator = fileValidator,
        lineValidator = lineValidator,
        emptyFieldValidator = emptyFieldValidator
    )

    val attendanceMapper: Mapper<AttendanceRaw, Attendance> = AttendanceMapper()
    val menteeMapper: Mapper<MenteeRaw, Mentee> = MenteeMapper()
    val performanceMapper: Mapper<PerformanceRaw, PerformanceSubmission> = PerformanceMapper()
    val projectMapper: Mapper<ProjectRaw, Project> = ProjectMapper()
    val teamMapper: Mapper<TeamRaw, Team> = TeamMapper()

    val attendanceRepo: AttendanceRepo = AttendanceRepoImpl(dataSource, attendanceMapper)
    val menteeRepo: MenteeRepo = MenteeRepoImpl(dataSource, menteeMapper)
    val performanceRepo: PerformanceRepo = PerformanceRepoImpl(dataSource, performanceMapper)
    val projectRepo: ProjectRepo = ProjectRepoImpl(dataSource, projectMapper)
    val teamRepo: TeamRepo = TeamRepoImpl(dataSource, teamMapper)
}

fun main() {


}
