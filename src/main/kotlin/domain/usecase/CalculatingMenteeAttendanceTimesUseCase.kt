package domain.usecase

import domain.model.entity.Attendance
import domain.repository.AttendanceRepo
import domain.model.entity.AttendanceStatus
import domain.model.entity.Mentee
import domain.repository.MenteeRepo

class CalculatingMenteeAttendanceTimesUseCase(
    private val menteeRepo: MenteeRepo,
    private val attendanceRepo: AttendanceRepo
) {
    operator fun invoke(): Result<Map<String, Int>> {
        val mentees = menteeRepo.getAll().getOrElse { return Result.failure(it) }
        val attendances = attendanceRepo.getAll().getOrElse { return Result.failure(it) }
        val result = mapMenteesToPresentCount(mentees, attendances)

        return Result.success(result)
    }

    private fun mapMenteesToPresentCount(
        mentees: List<Mentee>, allAttendances: List<Attendance>
    ): Map<String, Int> {
        val attendanceByMenteeId = allAttendances.groupBy { it.menteeId }
        return mentees.associate { mentee ->
            val menteeAttendances = attendanceByMenteeId[mentee.id].orEmpty()
            mentee.id to countPresent(menteeAttendances)
        }
    }

    private fun countPresent(attendances: List<Attendance>): Int =
        attendances.count { it.status == AttendanceStatus.PRESENT }
}