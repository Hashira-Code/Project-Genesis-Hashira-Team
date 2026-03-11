package domain.usecase
import domain.model.entity.AttendanceStatus
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
        val teams = teamRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val mentees = menteeRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val performances = performanceRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val attendances = attendanceRepo.getAll().getOrElse {
            return Result.failure(it)
        }

        val teamHealth = teams.associate { team ->
            val teamMenteeIds = getTeamMenteeIds(team.id, mentees)
            val avgPerformance =
                calculateAveragePerformance(teamMenteeIds, performances)
            val attendanceRate =
                calculateAttendanceRate(teamMenteeIds, attendances)
            team.name to evaluate(avgPerformance, attendanceRate)
        }

        return Result.success(teamHealth)
    }

    private fun getTeamMenteeIds(
        teamId: String,
        mentees: List<domain.model.entity.Mentee>
    ): List<String> =
        mentees
            .filter { it.teamId == teamId }
            .map { it.id }

    private fun calculateAveragePerformance(
        menteeIds: List<String>,
        performances: List<domain.model.entity.PerformanceSubmission>
    ): Double {
        val scores = performances
            .filter { it.menteeId in menteeIds }
            .map { it.score }
        return if (scores.isEmpty()) 0.0 else scores.average()
    }

    private fun calculateAttendanceRate(
        menteeIds: List<String>,
        attendances: List<domain.model.entity.Attendance>
    ): Double {
        val teamAttendances =
            attendances.filter { it.menteeId in menteeIds }
        return if (teamAttendances.isEmpty()) 0.0
        else
            teamAttendances.count { it.status == AttendanceStatus.PRESENT }
                .toDouble() / teamAttendances.size
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

