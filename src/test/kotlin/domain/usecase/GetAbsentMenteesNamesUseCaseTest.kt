package domain.usecase

import com.google.common.truth.Truth
import domain.model.exception.ValidationExeption
import domain.model.request.WeekNumberRequest
import domain.validation.WeekNumberValidator
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import data.BaseKoinTest
import data.fixture.TestDataFactory
import di.testModule
import domain.model.entity.AttendanceStatus
import kotlin.test.assertIs
import kotlin.test.assertTrue

@DisplayName("GetAbsentMenteesNamesUseCase")
class GetAbsentMenteesNamesUseCaseTest : BaseKoinTest() {
    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `returns success with absent mentee names when requested week has absences`() {
        TestDataFactory.reset()

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(1))

        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()).containsExactly("Aisha")
    }

    @Test
    fun `returns success with empty list when requested week has no absences`() {
        TestDataFactory.reset()
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m03", 1, AttendanceStatus.PRESENT),
        )
        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(1))

        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()).isEmpty()
    }

    @Test
    fun `returns failure with ValueOutOfRangeExeption when week number is not positive`() {
        TestDataFactory.reset()
        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(-2))

        assertTrue(result.isFailure)
        assertIs<ValidationExeption.ValueOutOfRangeExeption>(result.exceptionOrNull())
        Truth.assertThat(result.exceptionOrNull()?.message)
            .isEqualTo(WeekNumberValidator.NON_POSITIVE_ERROR)
    }


}

