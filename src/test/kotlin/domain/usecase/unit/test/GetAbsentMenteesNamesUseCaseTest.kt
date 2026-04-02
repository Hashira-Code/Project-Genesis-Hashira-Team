package domain.usecase.unit

import com.google.common.truth.Truth.assertThat
import di.defaultTestModules
import domain.model.entity.Attendance
import domain.model.entity.AttendanceStatus
import domain.model.exception.ValidationExeption
import domain.model.request.WeekNumberRequest
import domain.usecase.GetAbsentMenteesNamesUseCase
import domain.validation.WeekNumberValidator
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import support.BaseKoinTest
import support.Fixture
import kotlin.test.assertIs
import kotlin.test.assertTrue

@DisplayName("GetAbsentMenteesNamesUseCase")
class GetAbsentMenteesNamesUseCaseTest : BaseKoinTest() {

    private fun startTestKoin(attendance: List<Attendance> = Fixture.attendanceWeekOneWithOneAbsent()) {
        startKoinWith(
            *defaultTestModules(attendances = attendance)
        )
    }

    @Test
    fun `should return absent mentee names for the requested week`() {
        startTestKoin()

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(1))

        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()).containsExactly("Aisha")
    }

    @Test
    fun `should return empty list when no mentees are absent in the requested week`() {
        startTestKoin(
            attendance = Fixture.attendanceWeekOneAllPresent()
        )

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(1))

        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()).isEmpty()
    }

    @Test
    fun `should fail when week number is not positive`() {
        startTestKoin()

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(0))

        assertTrue(result.isFailure)
        assertIs<ValidationExeption.ValueOutOfRangeExeption>(result.exceptionOrNull())
        assertThat(result.exceptionOrNull()?.message)
            .isEqualTo(WeekNumberValidator.NON_POSITIVE_ERROR)
    }

    @Test
    fun `should ignore absences for other weeks`() {
        startTestKoin(
            attendance = Fixture.attendanceDifferentWeekAbsence()
        )

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(1))

        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()).isEmpty()
    }
}
