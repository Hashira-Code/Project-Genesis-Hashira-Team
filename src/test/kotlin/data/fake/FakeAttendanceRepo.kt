package data.fake

import domain.model.entity.Attendance
import domain.repository.AttendanceRepo

class FakeAttendanceRepo(
    private val attendances: List<Attendance> = emptyList()
): AttendanceRepo {
    override fun getAll(): Result<List<Attendance>> =
        Result.success(attendances)

    override fun getByMenteeId(menteeId: String): Result<List<Attendance>> =
        Result.success(attendances.filter { it.menteeId == menteeId })

    override fun getByWeekNumber(weekNumber: Int): Result<List<Attendance>> =
        Result.success(attendances.filter { it.weekNumber == weekNumber })
}