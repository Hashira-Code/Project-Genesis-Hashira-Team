package domain.usecase

import com.google.common.truth.Truth.assertThat
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

    private val getAbsentMenteesNamesUseCase: GetAbsentMenteesNamesUseCase by lazy { resolve() }

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `returns success with absent mentee names when requested week has absences`() {
        // Given: default data with absences in week 1

        // When: getting absent mentee names for week 1
        val result = getAbsentMenteesNamesUseCase(WeekNumberRequest(1))

        // Then: the result should be success and contain "Aisha"
        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()).containsExactly("Aisha")
    }

    @Test
    fun `returns success with empty list when requested week has no absences`() {
        // Given: all mentees are present in week 1
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m03", 1, AttendanceStatus.PRESENT),
        )

        // When: getting absent mentee names for week 1
        val result = getAbsentMenteesNamesUseCase(WeekNumberRequest(1))

        // Then: the result should be success with an empty list
        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()).isEmpty()
    }

    @Test
    fun `returns failure with ValueOutOfRangeExeption when week number is not positive`() {
        // Given: a non-positive week number

        // When: getting absent mentee names for week -2
        val result = getAbsentMenteesNamesUseCase(WeekNumberRequest(-2))

        // Then: the result should be failure with ValueOutOfRangeExeption
        assertTrue(result.isFailure)
        assertIs<ValidationExeption.ValueOutOfRangeExeption>(result.exceptionOrNull())
        assertThat(result.exceptionOrNull()?.message)
            .isEqualTo(WeekNumberValidator.NON_POSITIVE_ERROR)
    }

}