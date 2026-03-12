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
import domain.usecase.FindLeadMentorForMenteeUseCase
import domain.usecase.FindProjectsAssignedToTeamUseCase
import domain.usecase.GetAbsentMenteesNamesUseCase
import domain.usecase.FindTopScoringMenteeOverallUseCase
import domain.usecase.CalculatingMenteeAttendanceTimesUseCase
import domain.model.request.WeekNumberRequest
import domain.model.request.TeamIdRequest
import domain.model.request.MenteeIdRequest

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

    val findLeadMentorForMenteeUseCase = FindLeadMentorForMenteeUseCase(
        menteeRepo = menteeRepo,
        teamRepo = teamRepo
    )
    val findProjectsAssignedToTeamUseCase = FindProjectsAssignedToTeamUseCase(
        projectRepo = projectRepo
    )
    val getAbsentMenteesNamesUseCase =
        GetAbsentMenteesNamesUseCase(
            attendanceRepo = attendanceRepo,
            menteeRepo = menteeRepo,
            weekNumberValidator = weekNumberValidator
        )
    val findTopScoringMenteeOverallUseCase = FindTopScoringMenteeOverallUseCase(
        performanceRepo = performanceRepo,
        menteeRepo = menteeRepo
    )
    val calculatingMenteeAttendanceTimesUseCase = CalculatingMenteeAttendanceTimesUseCase(
        menteeRepo = menteeRepo,
        attendanceRepo = attendanceRepo
    )

}

fun main() {
    println("---  Calculating Attendance Times ---")
    AppContainer.calculatingMenteeAttendanceTimesUseCase().fold(
        onSuccess = { attendanceMap ->
            attendanceMap.forEach { (id, count) ->
                println("Mentee ID: $id | Attendance Count: $count")
            }
        },
        onFailure = { println("Error: ${it.message}") }
    )

    println("\n---  Finding Top Scoring Mentee ---")
    AppContainer.findTopScoringMenteeOverallUseCase().fold(
        onSuccess = { mentee ->
            if (mentee != null) println("Top Mentee: ${mentee.name}")
            else println("No mentees found.")
        },
        onFailure = { println("Error: ${it.message}") }
    )

    println("\n---  Getting Absent Mentees (Week 1) ---")
    val request = WeekNumberRequest(weekNumber = 1)
    AppContainer.getAbsentMenteesNamesUseCase(request).fold(
        onSuccess = { absentNames ->
            if (absentNames.isEmpty()) println("No one was absent!")
            else println("Absent Mentees: ${absentNames.joinToString(", ")}")
        },
        onFailure = { println("Validation Error: ${it.message}") }
    )

    println("\n---  Finding Projects Assigned to Team ---")
    val projectsRequest = TeamIdRequest(id = "T1")
    AppContainer.findProjectsAssignedToTeamUseCase(projectsRequest).fold(
        onSuccess = { projects ->
            println("Found ${projects.size} projects for this team:")
            projects.forEach { project ->
                println("- Project Name: ${project.name} (ID: ${project.id})")
            }
        },
        onFailure = { error ->
            println("Error: ${error.message}")
        }
    )

    println("\n---  Finding Lead Mentor ---")
    val leadMentorRequest = MenteeIdRequest(id = "M1")
    AppContainer.findLeadMentorForMenteeUseCase(leadMentorRequest).fold(
        onSuccess = { mentorName ->
            println("Lead Mentor Name: $mentorName")
        },
        onFailure = { error ->
            println("Error: ${error.message}")
        }
    )


}
