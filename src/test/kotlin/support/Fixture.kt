package support

import domain.model.entity.Attendance
import domain.model.entity.AttendanceStatus
import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.Project
import domain.model.entity.SubmissionType
import domain.model.entity.Team

object Fixture {

    fun teams() = listOf(
        Team.create("alpha", "Alpha Team", "Sarah"),
        Team.create("beta", "Beta Team", "Omar")
    )

    fun projects() = listOf(
        Project.create("p01", "Helios Initiative", "alpha"),
        Project.create("p02", "Nova Platform", "beta")
    )

    fun mentees() = listOf(
        Mentee.create("m01", "Aisha", "alpha"),
        Mentee.create("m02", "Sara", "alpha"),
        Mentee.create("m03", "Lina", "beta")
    )

    fun attendance() = listOf(
        Attendance("m01", 1, AttendanceStatus.ABSENT),
        Attendance("m02", 1, AttendanceStatus.PRESENT),
        Attendance("m03", 1, AttendanceStatus.PRESENT)
    )

    fun attendanceWeekOneAllPresent() = listOf(
        Attendance("m01", 1, AttendanceStatus.PRESENT),
        Attendance("m02", 1, AttendanceStatus.PRESENT),
        Attendance("m03", 1, AttendanceStatus.PRESENT)
    )

    fun attendanceDifferentWeekAbsence() = listOf(
        Attendance("m01", 2, AttendanceStatus.ABSENT),
        Attendance("m02", 1, AttendanceStatus.PRESENT),
        Attendance("m03", 1, AttendanceStatus.PRESENT)
    )

    /*  fun excellentTeamAttendance() = listOf(
          Attendance("m01", 1, AttendanceStatus.PRESENT),
          Attendance("m02", 1, AttendanceStatus.PRESENT)
      )*/

    /* fun poorTeamAttendance() = listOf(
         Attendance("m01", 1, AttendanceStatus.ABSENT),
         Attendance("m02", 1, AttendanceStatus.ABSENT)
     )*/

    fun performances() = listOf(
        PerformanceSubmission("s01", "m01", SubmissionType.TASK, 90.0),
        PerformanceSubmission("s02", "m02", SubmissionType.TASK, 90.0)
    )

    fun attendanceWeekOneWithOneAbsent() = attendance()

    /* fun tiedTaskSubmissions() = performances()*/

    /*  fun topTaskSubmissions() = listOf(
          PerformanceSubmission("s01", "m01", SubmissionType.TASK, 95.0),
          PerformanceSubmission("s02", "m02", SubmissionType.TASK, 80.0)
      )*/

    /* fun taskSubmissionsWithNegativeScore() = listOf(
         PerformanceSubmission("s01", "m01", SubmissionType.TASK, -10.0),
         PerformanceSubmission("s02", "m02", SubmissionType.TASK, 88.0)
     )*/


    /* fun averageTaskSubmissions() = listOf(
         PerformanceSubmission("s05", "m01", SubmissionType.TASK, 70.0),
         PerformanceSubmission("s06", "m02", SubmissionType.TASK, 72.0)
     )*/

    /* fun poorTaskSubmissions() = listOf(
         PerformanceSubmission("s07", "m01", SubmissionType.TASK, 45.0),
         PerformanceSubmission("s08", "m02", SubmissionType.TASK, 30.0)
     )
     */
}
