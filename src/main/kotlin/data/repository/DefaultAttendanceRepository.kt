package data.repository

import data.dataSource
import domain.model.Attendance
import domain.repository.AttendanceRepository
import mapper.Mapper
import dataRaw.AttendanceRaw

class DefaultAttendanceRepository(
    private val dataSource: dataSource,
    private val mapper: Mapper<AttendanceRaw, Attendance>
) : AttendanceRepository {

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