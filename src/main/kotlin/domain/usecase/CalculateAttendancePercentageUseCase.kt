package domain.usecase

import domain.model.entity.Attendance
import domain.model.entity.Mentee
import domain.repository.AttendanceRepo
import domain.repository.MenteeRepo

class CalculateAttendancePercentageUseCase(
    private val menteeRepo: MenteeRepo,
    private val attendanceRepo: AttendanceRepo,
    private val attendanceTimes: CalculatingMenteeAttendanceTimesUseCase
) {
    operator fun invoke(): Result<Map<Mentee, Double>> {
        val attendances = attendanceRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val totalWeeks = getTotalNumberOfWeeks(attendances)
        if (totalWeeks == 0) return Result.success(emptyMap())

        val attendanceCountByMentee = attendanceTimes().getOrElse {
            return Result.failure(it)
        }
        val mentees = menteeRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        return Result.success(
            calculatePercentages(mentees, attendanceCountByMentee, totalWeeks)
        )
    }

    private fun getTotalNumberOfWeeks(attendance: List<Attendance>): Int =
        attendance.map { it.weekNumber }.distinct().size

    private fun calculatePercentages(
        mentees: List<Mentee>,
        attendanceCount: Map<String, Int>,
        totalWeeks: Int
    ): Map<Mentee, Double> =
        mentees.associateWith { mentee ->
            val attendedWeeks = attendanceCount[mentee.id] ?: 0
            attendedWeeks.toDouble() / totalWeeks * PERCENTAGE_MULTIPLIER
        }
    companion object {
        private const val PERCENTAGE_MULTIPLIER = 100.0
    }
}
