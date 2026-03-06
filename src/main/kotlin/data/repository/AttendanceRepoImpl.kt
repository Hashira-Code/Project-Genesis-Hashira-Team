package data.repository

import data.dataSource.DataSource
import domain.model.Attendance
import domain.repository.AttendanceRepo
import data.mapper.Mapper
import data.model.AttendanceRaw

class AttendanceRepoImpl(
    private val dataSource: DataSource,
    private val mapper: Mapper<AttendanceRaw, Attendance>
) : AttendanceRepo {

    private val cache: Result<List<Attendance>> by lazy {
        runCatching { mapper.toDomain(dataSource.getAllAttendance()) }
    }

    private val byMenteeId: Map<String, List<Attendance>> by lazy {
        cache.getOrDefault(emptyList()).groupBy { it.menteeId }
    }

    private val byWeekNumber: Map<Int, List<Attendance>> by lazy {
        cache.getOrDefault(emptyList()).groupBy { it.weekNumber }
    }

    override fun getAll(): Result<List<Attendance>> = cache

    override fun getByMenteeId(menteeId: String): Result<List<Attendance>> =
        cache.map { byMenteeId[menteeId].orEmpty() }

    override fun getByWeekNumber(weekNumber: Int): Result<List<Attendance>> =
        cache.map { byWeekNumber[weekNumber].orEmpty() }
}