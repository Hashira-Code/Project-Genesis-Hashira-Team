package domain.repository

import domain.model.Attendance

interface AttendanceRepository {
    fun getAll(): List<Attendance>
    fun getByMenteeId(menteeId: String): List<Attendance>
    fun getByWeekNumber(weekNumber: Int): List<Attendance>
}
