package domain.usecase

import domain.model.entity.Attendance
import domain.model.request.MenteeIdRequest
import domain.repository.AttendanceRepo
import domain.model.entity.AttendanceStatus

class CalculateOverallAttendancePercentageUseCase(private val attendanceRepo: AttendanceRepo){

    operator fun invoke(menteeId: MenteeIdRequest):Result<Double>{
        val attendances: List<Attendance> = attendanceRepo.getByMenteeId(menteeId.id).getOrElse {
            return Result.failure(it)
        }

        val points = attendances.sumOf { attendance ->
            when (attendance.status) {
                AttendanceStatus.PRESENT -> 1.0
                AttendanceStatus.LATE -> 0.5
                AttendanceStatus.ABSENT -> 0.0
            }
        }

        val percentage = (points / TOTAL_SESSIONS) * 100

        return Result.success(percentage)
    }

    companion object {
        const val TOTAL_SESSIONS: Int = 3
    }
}

