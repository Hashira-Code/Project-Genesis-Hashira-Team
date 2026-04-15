package domain.usecase

import domain.model.entity.Attendance
import domain.model.request.MenteeIdRequest
import domain.repository.AttendanceRepo
import domain.model.entity.AttendanceStatus

class CalculateOverallAttendancePercentageUseCase(private val attendanceRepo: AttendanceRepo){

    operator fun invoke(menteeId: MenteeIdRequest):Result<Double>{
       TODO()
}

}