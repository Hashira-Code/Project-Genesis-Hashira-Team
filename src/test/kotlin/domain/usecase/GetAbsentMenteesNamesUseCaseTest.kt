package domain.usecase

import com.google.common.truth.Truth
import di.defaultTestModules
import domain.model.exception.ValidationExeption
import domain.model.request.WeekNumberRequest
import domain.validation.WeekNumberValidator
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import data.BaseKoinTest
import data.fixture.GetAbsentMenteesNamesFixture
import kotlin.test.assertIs
import kotlin.test.assertTrue

@DisplayName("GetAbsentMenteesNamesUseCase")
class GetAbsentMenteesNamesUseCaseTest : BaseKoinTest() {

    private fun startTestKoin(testCase: GetAbsentMenteesNamesFixture.Case) {
        startKoinWith(
            *defaultTestModules(attendances = testCase.attendance)
        )
    }

    @Test
    fun `returns success with absent mentee names when requested week has absences`() {
        val case = GetAbsentMenteesNamesFixture.returnsAbsentMenteeNamesForRequestedWeek
        startTestKoin(case)

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(case.requestWeek))

        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()).containsExactlyElementsIn(case.expectedNames)
    }

    @Test
    fun `returns success with empty list when requested week has no absences`() {
        val case = GetAbsentMenteesNamesFixture.returnsEmptyListWhenNoAbsencesExistInRequestedWeek
        startTestKoin(case)

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(case.requestWeek))

        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()).containsExactlyElementsIn(case.expectedNames)
    }

    @Test
    fun `returns failure with ValueOutOfRangeExeption when week number is not positive`() {
        val case = GetAbsentMenteesNamesFixture.failsWhenWeekNumberIsNotPositive
        startTestKoin(case)

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(case.requestWeek))

        assertTrue(result.isFailure)
        assertIs<ValidationExeption.ValueOutOfRangeExeption>(result.exceptionOrNull())
        Truth.assertThat(result.exceptionOrNull()?.message)
            .isEqualTo(case.expectedErrorMessage ?: WeekNumberValidator.NON_POSITIVE_ERROR)
    }

    @Test
    fun `returns success with empty list when absences exist only in other weeks`() {
        val case = GetAbsentMenteesNamesFixture.ignoresAbsencesFromOtherWeeks
        startTestKoin(case)

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(case.requestWeek))

        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()).containsExactlyElementsIn(case.expectedNames)
    }
}
