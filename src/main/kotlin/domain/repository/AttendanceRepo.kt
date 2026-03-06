package domain.repository

import domain.model.Attendance

interface AttendanceRepo {
    fun getAll(): Result<List<Attendance>>
    fun getByMenteeId(menteeId: String): Result<List<Attendance>>
    fun getByWeekNumber(weekNumber: Int): Result<List<Attendance>>
}
