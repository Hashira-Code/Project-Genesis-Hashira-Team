package support

import domain.model.entity.Attendance
import domain.model.entity.AttendanceStatus
import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType

object Fixture {
    fun  performanceSubmissionList() = listOf(
        PerformanceSubmission("s01", "m01", SubmissionType.TASK, 90.0),
        PerformanceSubmission("s02", "m02", SubmissionType.TASK, 90.0)
    )

    fun workshopPerformanceSubmissionList() = listOf(
        PerformanceSubmission("s03", "m01", SubmissionType.WORKSHOP, 90.0),
        PerformanceSubmission("s04", "m02", SubmissionType.WORKSHOP, 88.0)
    )

    fun averagePerformanceSubmissionList() = listOf(
        PerformanceSubmission(id = "s05", menteeId = "m01", type = SubmissionType.TASK, score = 70.0),
        PerformanceSubmission(id = "s06", menteeId = "m02", type = SubmissionType.TASK, score = 72.0)
    )

    fun poorPerformanceSubmissionList() = listOf(
        PerformanceSubmission(id = "s07", menteeId = "m01", type = SubmissionType.TASK, score = 45.0),
        PerformanceSubmission(id = "s08", menteeId = "m02", type = SubmissionType.TASK, score = 30.0)
    )

    fun perfectAttendanceList(menteeId1: String = "m01", menteeId2: String = "m02") = listOf(
        Attendance(menteeId = menteeId1, weekNumber = 1, status = AttendanceStatus.PRESENT),
        Attendance(menteeId = menteeId2, weekNumber = 1, status = AttendanceStatus.PRESENT)
    )

    fun lowAttendanceList(menteeId: String = "m01") = listOf(
        Attendance(menteeId = menteeId, weekNumber = 1, status = AttendanceStatus.ABSENT)
    )
}