package data.repository

import data.source.DataSource
import domain.model.Attendance
import domain.repository.AttendanceRepo
import data.mapper.Mapper
import data.model.AttendanceRaw

class AttendanceRepoImpl(
    private val dataSource: DataSource,
    private val mapper: Mapper<AttendanceRaw, Attendance>
) : AttendanceRepo {

    private val cache: List<Attendance> by lazy {
        mapper.toDomain(dataSource.getAllAttendance())
    }

    private val byMenteeId: Map<String, List<Attendance>> by lazy {
        cache.groupBy { it.menteeId }
    }

    private val byWeekNumber: Map<Int, List<Attendance>> by lazy {
        cache.groupBy { it.weekNumber }
    }

    override fun getAll(): List<Attendance> = cache

    override fun getByMenteeId(menteeId: String): List<Attendance> =
        byMenteeId[menteeId].orEmpty()

    override fun getByWeekNumber(weekNumber: Int): List<Attendance> =
        byWeekNumber[weekNumber].orEmpty()
}