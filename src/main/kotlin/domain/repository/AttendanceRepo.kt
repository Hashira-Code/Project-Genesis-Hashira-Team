package domain.repository

import domain.model.entity.Attendance

interface AttendanceRepo {
    fun getAll(): Result<List<Attendance>>
    fun getByMenteeId(menteeId: String): Result<List<Attendance>>
    fun getByWeekNumber(weekNumber: Int): Result<List<Attendance>>
}


