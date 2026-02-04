package domain.usecase
import domain.model.AttendanceStatus
import domain.model.TeamHealthStatus
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
    operator fun invoke(): Map<String, TeamHealthStatus> {
        val teams = teamRepo.getAll()
        val mentees = menteeRepo.getAll()
        val performances = performanceRepo.getAll()
        val attendances = attendanceRepo.getAll()
        return teams.associate { team ->
            val teamMenteeIds = getTeamMenteeIds(team.id, mentees)
            val avgPerformance =
                calculateAveragePerformance(teamMenteeIds, performances)
            val attendanceRate =
                calculateAttendanceRate(teamMenteeIds, attendances)
            team.name to evaluate(avgPerformance, attendanceRate)
        }
    }
    private fun getTeamMenteeIds(
        teamId: String,
        mentees: List<domain.model.Mentee>
    ): List<String> =
        mentees
            .filter { it.teamId == teamId }
            .map { it.id }
    private fun calculateAveragePerformance(
        menteeIds: List<String>,
        performances: List<domain.model.PerformanceSubmission>
    ): Double =
        performances
            .filter { it.menteeId in menteeIds }
            .map { it.score }
            .average()
    private fun calculateAttendanceRate(
        menteeIds: List<String>,
        attendances: List<domain.model.Attendance>
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
            avgPerformance >= 80 && attendanceRate >= 0.9 ->
                TeamHealthStatus.EXCELLENT
            avgPerformance >= 60 && attendanceRate >= 0.7 ->
                TeamHealthStatus.GOOD
            else ->
                TeamHealthStatus.NEEDS_ATTENTION
        }
}