package domain.repository

import domain.model.entity.Attendance

interface AttendanceRepo {
    suspend fun getAll(): Result<List<Attendance>>
    suspend fun getByMenteeId(menteeId: String): Result<List<Attendance>>
    suspend fun getByWeekNumber(weekNumber: Int): Result<List<Attendance>>
}


