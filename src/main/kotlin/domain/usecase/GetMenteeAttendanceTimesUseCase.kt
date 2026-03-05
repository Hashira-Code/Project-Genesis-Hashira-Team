package domain.usecase

import domain.repository.AttendanceRepo
import domain.model.AttendanceStatus
import domain.repository.MenteeRepo

class GetMenteeAttendanceTimesUseCase(
    private val menteeRepo: MenteeRepo,
    private val attendanceRepo: AttendanceRepo
) {
    operator fun invoke(): Map<String, Int> {
        val attendance = attendanceRepo.getAll()

        return menteeRepo.getAll().associate { mentee ->
            val attendanceTimes = attendance.count {
                it.menteeId == mentee.id && it.status == AttendanceStatus.PRESENT
            }
            mentee.id to attendanceTimes
        }
    }
}