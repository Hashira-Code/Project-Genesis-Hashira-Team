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

    override fun getAll(): List<Attendance> = cache




}