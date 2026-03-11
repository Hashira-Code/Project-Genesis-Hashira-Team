package domain.usecase

import domain.repository.AttendanceRepo
import domain.model.entity.AttendanceStatus
import domain.repository.MenteeRepo

class CalculatingMenteeAttendanceTimesUseCase(
    private val menteeRepo: MenteeRepo,
    private val attendanceRepo: AttendanceRepo
) {
    operator fun invoke(): Result<Map<String, Int>> {
        val mentees = menteeRepo.getAll().getOrElse {
            return Result.failure(it)
        }

        val attendanceTimes = mentees.associate { mentee ->
            val attendances = attendanceRepo.getByMenteeId(mentee.id).getOrElse {
                return Result.failure(it)
            }

            val presentCount = attendances.count { it.status == AttendanceStatus.PRESENT }
            mentee.id to presentCount
        }

        return Result.success(attendanceTimes)
    }
}

