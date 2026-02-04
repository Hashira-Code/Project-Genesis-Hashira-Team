package domain.usecase
import domain.repository.MenteeRepo
import domain.repository.TeamRepo

class TeamAttendanceReportUseCase(
    private val teamRepo: TeamRepo,
    private val menteeRepo: MenteeRepo,
    private val calculateAttendancePercentage: CalculateAttendancePercentageUseCase
) {
    operator fun invoke(): Map<String, List<Pair<String, Double>>> {
            val teams = teamRepo.getAll()
            val mentees = menteeRepo.getAll()
            val attendancePercentages = calculateAttendancePercentage()
            return teams.associate { team ->
                val teamMembers =
                    mentees
                        .filter { it.teamId == team.id }
                        .map { mentee ->
                            mentee.name to (attendancePercentages[mentee] ?: 0.0)
                        }
                team.name to teamMembers
            }
        }
    }