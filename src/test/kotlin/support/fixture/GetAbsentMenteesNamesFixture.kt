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

    val returnsAbsentMenteeNamesForRequestedWeek = Case(
        name = "returns absent mentee names for requested week",
        attendance = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m03", 1, AttendanceStatus.PRESENT)
        ),
        requestWeek = 1,
        expectedNames = listOf("Aisha")
    )

    val returnsEmptyListWhenNoAbsencesExistInRequestedWeek = Case(
        name = "returns empty list when no absences exist in requested week",
        attendance = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m03", 1, AttendanceStatus.PRESENT)
        ),
        requestWeek = 1,
        expectedNames = emptyList()
    )

    val ignoresAbsencesFromOtherWeeks = Case(
        name = "ignores absences from other weeks",
        attendance = listOf(
            TestDataFactory.attendance("m01", 2, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m03", 1, AttendanceStatus.PRESENT)
        ),
        requestWeek = 1,
        expectedNames = emptyList()
    )

    val failsWhenWeekNumberIsNotPositive = Case(
        name = "fails when week number is not positive",
        attendance = returnsAbsentMenteeNamesForRequestedWeek.attendance,
        requestWeek = 0,
        expectedErrorMessage = WeekNumberValidator.NON_POSITIVE_ERROR
    )
}
