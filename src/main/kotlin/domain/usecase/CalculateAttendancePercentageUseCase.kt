package domain.usecase

import domain.model.Attendance
import domain.model.AttendanceStatus
import domain.model.Mentee
import domain.repository.AttendanceRepo
import domain.repository.MenteeRepo

class CalculateAttendancePercentageUseCase(
    private val menteeRepo: MenteeRepo,
    private val attendanceRepo: AttendanceRepo,
    private val attendanceTimes:CalculatingMenteeAttendanceTimesUseCase
) {

    operator fun invoke():Map<Mentee, Double>{
        val totalWeeks=getTotalNumberOfWeeks(attendanceRepo.getAll())

        return menteeRepo.getAll().associate { mentee ->
           val percentageOfAttendance=((attendanceTimes()[mentee.id]?:0).toDouble()/totalWeeks)*PERCENTAGE_MULTIPLIER

            mentee to percentageOfAttendance
        }
    }

    private fun getTotalNumberOfWeeks(attendance: List<Attendance>): Int=
        attendance.map{it.weekNumber}.distinct().size
    companion object {
        private const val PERCENTAGE_MULTIPLIER=100.0

    }

}


