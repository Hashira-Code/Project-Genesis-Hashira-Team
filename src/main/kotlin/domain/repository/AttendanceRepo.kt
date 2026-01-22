package domain.repository

import domain.model.Attendance

interface AttendanceRepo {
    fun getAll(): List<Attendance>
    fun getByMenteeId(menteeId: String): List<Attendance>
    fun getByWeekNumber(weekNumber: Int): List<Attendance>
}
