package domain.usecase

import domain.model.entity.Attendance
import domain.model.entity.AttendanceStatus
import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.Team
import domain.model.entity.TeamHealthStatus
import domain.repository.AttendanceRepo
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import domain.repository.TeamRepo

class EvaluateTeamHealthUseCase(
    private val teamRepo: TeamRepo,
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo,
    private val attendanceRepo: AttendanceRepo
) {
    operator fun invoke(): Result<Map<String, TeamHealthStatus>> {
        val teams = teamRepo.getAll().getOrElse { return Result.failure(it) }
        val mentees = menteeRepo.getAll().getOrElse { return Result.failure(it) }
        val performances = performanceRepo.getAll().getOrElse { return Result.failure(it) }
        val attendances = attendanceRepo.getAll().getOrElse { return Result.failure(it) }

        val teamHealthReport = buildHealthReport(teams, mentees, performances, attendances)

        return Result.success(teamHealthReport)
    }

    private fun buildHealthReport(
        teams: List<Team>,
        mentees: List<Mentee>,
        performances: List<PerformanceSubmission>,
        attendances: List<Attendance>
    ): Map<String, TeamHealthStatus> {
        val menteesByTeam = mentees.groupBy { it.teamId }
        val performancesByMentee = performances.groupBy { it.menteeId }
        val attendancesByMentee = attendances.groupBy { it.menteeId }

        return teams.associate { team ->
            val teamMenteesIds = menteesByTeam[team.id].orEmpty().map { it.id }

            val avgPerformance = calculateAveragePerformance(teamMenteesIds, performancesByMentee)
            val attendanceRate = calculateAttendanceRate(teamMenteesIds, attendancesByMentee)

            team.name to evaluate(avgPerformance, attendanceRate)
        }
    }

    private fun calculateAveragePerformance(
        menteeIds: List<String>,
        performancesByMentee: Map<String, List<PerformanceSubmission>>
    ): Double {
        val scores = menteeIds.flatMap { performancesByMentee[it].orEmpty() }
            .map { it.score }
        return if (scores.isEmpty()) 0.0 else scores.average()
    }

    private fun calculateAttendanceRate(
        menteeIds: List<String>,
        attendancesByMentee: Map<String, List<Attendance>>
    ): Double {
        val allAttendances = menteeIds.flatMap { attendancesByMentee[it].orEmpty() }
        return if (allAttendances.isEmpty()) 0.0
        else allAttendances.count { it.status == AttendanceStatus.PRESENT }.toDouble() / allAttendances.size
    }

    private fun evaluate(
        avgPerformance: Double,
        attendanceRate: Double
    ): TeamHealthStatus =
        when {
            avgPerformance >= EXCELLENT_PERFORMANCE_THRESHOLD && attendanceRate >= EXCELLENT_ATTENDANCE_THRESHOLD ->
                TeamHealthStatus.EXCELLENT

            avgPerformance >= GOOD_PERFORMANCE_THRESHOLD && attendanceRate >= GOOD_ATTENDANCE_THRESHOLD ->
                TeamHealthStatus.GOOD

            else ->
                TeamHealthStatus.NEEDS_ATTENTION
        }

    companion object {
        private const val EXCELLENT_PERFORMANCE_THRESHOLD = 80.0
        private const val GOOD_PERFORMANCE_THRESHOLD = 60.0
        private const val EXCELLENT_ATTENDANCE_THRESHOLD = 0.9
        private const val GOOD_ATTENDANCE_THRESHOLD = 0.7
    }
}