package domain.usecase

import domain.repository.AttendanceRepo
import domain.model.entity.AttendanceStatus
import domain.repository.MenteeRepo

class CalculatingMenteeAttendanceTimesUseCase(
    private val menteeRepo: MenteeRepo,
    private val attendanceRepo: AttendanceRepo
)  {
    operator fun invoke():Map<String,Int> {
        val attendance=attendanceRepo.getAll().getOrThrow()

        return menteeRepo.getAll().getOrThrow().associate{ mentee ->
                val AttendanceTimes=attendance.count{
                    it.menteeId==mentee.id && it.status==AttendanceStatus.PRESENT
                }
              mentee.id to AttendanceTimes
    }
}}

