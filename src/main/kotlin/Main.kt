import org.koin.core.context.GlobalContext.startKoin
import domain.usecase.CalculatingMenteeAttendanceTimesUseCase
import domain.usecase.FindProjectsAssignedToTeamUseCase
import domain.usecase.GetAbsentMenteesNamesUseCase
import di.appModules
import domain.model.request.MenteeIdRequest
import domain.model.request.TeamIdRequest
import domain.model.request.WeekNumberRequest
import domain.usecase.FindLeadMentorForMenteeUseCase
import domain.usecase.FindTopScoringMenteeOverallUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// Main.kt
fun main() {

    startKoin { modules(appModules) }
    App().run()
}


class App : KoinComponent {


    private val calcAttendance: CalculatingMenteeAttendanceTimesUseCase by inject()
    private val findTopScoring: FindTopScoringMenteeOverallUseCase by inject()
    private val getAbsent: GetAbsentMenteesNamesUseCase by inject()
    private val findProjects: FindProjectsAssignedToTeamUseCase by inject()
    private val findLeadMentor: FindLeadMentorForMenteeUseCase by inject()

    fun run() {
        printSection("Calculating Attendance Times") {
            calcAttendance().fold(
                onSuccess = { map ->
                    map.forEach { (id, count) ->
                        println("Mentee ID: $id | Attendance Count: $count")
                    }
                },
                onFailure = { println("Error: ${it.message}") }
            )
        }

        printSection("Finding Top Scoring Mentee") {
            findTopScoring().fold(
                onSuccess = { mentee ->
                    if (mentee != null) println("Top Mentee: ${mentee.name}")
                    else println("No mentees found.")
                },
                onFailure = { println("Error: ${it.message}") }
            )
        }

        printSection("Getting Absent Mentees (Week 1)") {
            getAbsent(WeekNumberRequest(1)).fold(
                onSuccess = { names ->
                    if (names.isEmpty()) println("No one was absent!")
                    else println("Absent Mentees: ${names.joinToString(", ")}")
                },
                onFailure = { println("Validation Error: ${it.message}") }
            )
        }

        printSection("Finding Projects Assigned to Team") {
            findProjects(TeamIdRequest("alpha")).fold(
                onSuccess = { projects ->
                    println("Found ${projects.size} projects:")
                    projects.forEach { println("- ${it.name} (${it.id})") }
                },
                onFailure = { println("Error: ${it.message}") }
            )
        }

        printSection("Finding Lead Mentor") {
            findLeadMentor(MenteeIdRequest("m01")).fold(
                onSuccess = { println("Lead Mentor: $it") },
                onFailure = { println("Error: ${it.message}") }
            )
        }
    }

    private inline fun printSection(title: String, block: () -> Unit) {
        println("\n--- $title ---")
        block()
    }
}