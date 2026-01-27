package domain.repository

import domain.model.Attendance

interface AttendanceRepo {
    fun getAll(): List<Attendance>

}
