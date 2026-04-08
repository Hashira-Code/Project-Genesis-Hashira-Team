package support.fixture

import domain.model.entity.Attendance
import domain.model.entity.AttendanceStatus
import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType
import domain.model.entity.TeamHealthStatus

object EvaluateTeamHealthFixture {
     data class Case(
         val name: String,
         val attendance: List<Attendance>,
         val performances: List<PerformanceSubmission>,
         val expectedAlphaTeamStatus: TeamHealthStatus
     )

    val excellent = Case(
        "excellent when performance and attendance exceed thresholds",
     listOf(
    TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT),
    TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
     TestDataFactory.attendance("m03", 1, AttendanceStatus.PRESENT)
    ),
    listOf(
    TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 90.0),
    TestDataFactory.submission("s02", "m02", SubmissionType.TASK, 88.0),
     TestDataFactory.submission("s03", "m03", SubmissionType.TASK, 92.0)
     ),
    TeamHealthStatus.EXCELLENT
   )

    val good = Case(
        "good when performance is average and attendance is acceptable",
    listOf(
  TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT),
    TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
    TestDataFactory.attendance("m03", 1, AttendanceStatus.ABSENT)
    ),
     listOf(
     TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 70.0),
   TestDataFactory.submission("s02", "m02", SubmissionType.TASK, 68.0),
    TestDataFactory.submission("s03", "m03", SubmissionType.TASK, 65.0)
     ),
     TeamHealthStatus.GOOD
     )

     val needsAttention = Case(
         "needs attention when both signals are weak",
    listOf(
    TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
    TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
    TestDataFactory.attendance("m03", 1, AttendanceStatus.ABSENT)
   ),
    listOf(
    TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 45.0),
    TestDataFactory.submission("s02", "m02", SubmissionType.TASK, 50.0),
    TestDataFactory.submission("s03", "m03", SubmissionType.TASK, 40.0)
    ),
     TeamHealthStatus.NEEDS_ATTENTION
     )
}