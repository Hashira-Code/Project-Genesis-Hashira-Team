package domain.useCase

import domain.model.Attendance
import domain.model.AttendanceStatus
import domain.model.Mentee
import domain.repository.AttendanceRepo
import domain.repository.MenteeRepo

class calculateAttendancePercentageUseCase(
    private val menteeRepo: MenteeRepo,
    private val attendanceRepo: AttendanceRepo
) {

    fun execute():List<Pair<Mentee, Double>>{
        val totalWeeks=totalWeeks(attendanceRepo.getAll())
        val MenteeAttendanceWeeks=numOfMenteeAttendanceWeeks(menteeRepo.getAll(),attendanceRepo.getAll())
        return menteeRepo.getAll().zip(percentageOfAttendance(totalWeeks,MenteeAttendanceWeeks))
    }

    private fun totalWeeks(attendance: List<Attendance>): Int=
        attendance.map{it.weekNumber}.distinct().size


    private fun numOfMenteeAttendanceWeeks(mentee: List<Mentee>,attendanceList: List<Attendance>): List<Int>{
        return mentee.map { mentee ->
            attendanceList.filter { it.menteeId==mentee.id}
                .map { it.status }
                .filter { it==AttendanceStatus.PRESENT }
                .size
        }
    }

    private fun percentageOfAttendance(totalWeeks: Int, listOfmenteeAttendane: List<Int>): List<Double>{
        return listOfmenteeAttendane.map{numOfWeekAttendance ->
            (numOfWeekAttendance/totalWeeks)*100.0
        }
    }


}