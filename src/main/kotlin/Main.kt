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
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun main() = runBlocking {
    startKoin { modules(appModules) }
    App().runCommands()
}

class App : KoinComponent {

    private val calcAttendance: CalculatingMenteeAttendanceTimesUseCase by inject()
    private val findTopScoring: FindTopScoringMenteeOverallUseCase by inject()
    private val getAbsent: GetAbsentMenteesNamesUseCase by inject()
    private val findProjects: FindProjectsAssignedToTeamUseCase by inject()
    private val findLeadMentor: FindLeadMentorForMenteeUseCase by inject()

    suspend fun runCommands() = supervisorScope {

        commands()
            .map { command ->
                launch {
                    printSection(command.title)
                    try {
                        command.execute()
                    } catch (exception: CancellationException) {
                        throw exception
                    } catch (exception: Exception) {
                        println("Unexpected Error: ${exception.message}")
                    }
                }
            }
            .forEach { job ->
                job.join()
            }
    }

    private fun commands(): List<AppCommand> =
        listOf(
            AppCommand("Calculating Attendance Times") {
                calcAttendance().fold(
                    onSuccess = { map ->
                        map.forEach { (id, count) ->
                            println("Mentee ID: $id | Attendance Count: $count")
                        }
                    },
                    onFailure = {
                        println("Error: ${it.message}")
                    }
                )
            },

            AppCommand("Finding Top Scoring Mentee") {

                findTopScoring().fold(
                    onSuccess = { mentee ->
                        if (mentee != null) {
                            println("Top Mentee: ${mentee.name}")
                        } else {
                            println("No mentees found.")
                        }
                    },
                    onFailure = {
                        println("Error: ${it.message}")
                    }
                )
            },

            AppCommand("Getting Absent Mentees (Week 1)") {

                getAbsent(WeekNumberRequest(1)).fold(

                    onSuccess = { names ->
                        if (names.isEmpty()) {
                            println("No one was absent!")
                        } else {
                            println("Absent Mentees: ${names.joinToString(", ")}")
                        }
                    },
                    onFailure = {
                        println("Validation Error: ${it.message}")
                    }
                )
            },

            AppCommand("Finding Projects Assigned to Team") {
                findProjects(TeamIdRequest("alpha")).fold(
                    onSuccess = { projects ->
                        println("Found ${projects.size} projects:")
                        projects.forEach { project ->
                            println("- ${project.name} (${project.id})")
                        }
                    },
                    onFailure = {
                        println("Error: ${it.message}")
                    }
                )
            },

            AppCommand("Finding Lead Mentor") {
                findLeadMentor(MenteeIdRequest("m01")).fold(
                    onSuccess = {
                        println("Lead Mentor: $it")
                    },
                    onFailure = {
                        println("Error: ${it.message}")
                    }
                )
            }
        )

    private fun printSection(title: String) {
        println("\n--- $title ---")
    }

    private data class AppCommand(
        val title: String,
        val execute: suspend () -> Unit
    )
}