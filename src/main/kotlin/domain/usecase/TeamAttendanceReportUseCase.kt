package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.Team
import domain.repository.MenteeRepo
import domain.repository.TeamRepo

class TeamAttendanceReportUseCase(
    private val teamRepo: TeamRepo,
    private val menteeRepo: MenteeRepo,
    private val calculateAttendancePercentage: CalculateAttendancePercentageUseCase
) {
    operator fun invoke(): Result<Map<String, List<Pair<String, Double>>>> {
        val teams = teamRepo.getAll().getOrElse { return Result.failure(it) }
        val mentees = menteeRepo.getAll().getOrElse { return Result.failure(it) }
        val attendancePercentages = calculateAttendancePercentage().getOrElse {
            return Result.failure(it)
        }
        val report = buildAttendanceReport(teams, mentees, attendancePercentages)

        return Result.success(report)
    }

    private fun buildAttendanceReport(
        teams: List<Team>,
        mentees: List<Mentee>,
        attendancePercentages: Map<Mentee, Double>
    ): Map<String, List<Pair<String, Double>>> {
        return teams.associate { team ->
            val teamMembers = filterAndMapTeamMembers(team.id, mentees, attendancePercentages)
            team.name to teamMembers
        }
    }

    private fun filterAndMapTeamMembers(
        teamId: String,
        mentees: List<Mentee>,
        attendancePercentages: Map<Mentee, Double>
    ): List<Pair<String, Double>> {
        return mentees
            .filter { it.teamId == teamId }
            .map { mentee ->
                mentee.name to (attendancePercentages[mentee] ?: 0.0)
            }
    }
}