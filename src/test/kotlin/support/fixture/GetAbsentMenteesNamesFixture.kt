package support.fixture

import domain.model.entity.Attendance
import domain.model.entity.AttendanceStatus
import domain.validation.WeekNumberValidator

object GetAbsentMenteesNamesFixture {
    data class Case(
        val name: String,
        val attendance: List<Attendance>,
        val requestWeek: Int,
        val expectedNames: List<String> = emptyList(),
        val expectedErrorMessage: String? = null
    )

    val requestedWeekWithOneAbsent = Case(
        name = "requested week contains one absent mentee",
        attendance = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m03", 1, AttendanceStatus.PRESENT)
        ),
        requestWeek = 1,
        expectedNames = listOf("Aisha")
    )

    val requestedWeekWithNoAbsences = Case(
        name = "requested week contains no absences",
        attendance = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m03", 1, AttendanceStatus.PRESENT)
        ),
        requestWeek = 1,
        expectedNames = emptyList()
    )

    val absencesExistInAnotherWeekOnly = Case(
        name = "absences exist in another week only",
        attendance = listOf(
            TestDataFactory.attendance("m01", 2, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m03", 1, AttendanceStatus.PRESENT)
        ),
        requestWeek = 1,
        expectedNames = emptyList()
    )

    val duplicatedAbsenceRows = Case(
        name = "duplicated absence rows are de-duplicated",
        attendance = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT)
        ),
        requestWeek = 1,
        expectedNames = listOf("Aisha")
    )

    val missingAttendanceForRequestedWeek = Case(
        name = "requested week has no attendance rows",
        attendance = emptyList(),
        requestWeek = 1,
        expectedNames = emptyList()
    )

    val invalidWeekNumber = Case(
        name = "week number must be positive",
        attendance = requestedWeekWithOneAbsent.attendance,
        requestWeek = 0,
        expectedErrorMessage = WeekNumberValidator.NON_POSITIVE_ERROR
    )

    val cases = listOf(
        requestedWeekWithOneAbsent,
        requestedWeekWithNoAbsences,
        absencesExistInAnotherWeekOnly,
        duplicatedAbsenceRows,
        missingAttendanceForRequestedWeek,
        invalidWeekNumber
    )
}
