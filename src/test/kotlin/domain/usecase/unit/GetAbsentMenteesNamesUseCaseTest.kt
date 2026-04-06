package domain.usecase.unit

import com.google.common.truth.Truth
import di.defaultTestModules
import domain.model.exception.ValidationExeption
import domain.model.request.WeekNumberRequest
import domain.usecase.GetAbsentMenteesNamesUseCase
import domain.validation.WeekNumberValidator
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import support.BaseKoinTest
import support.fixture.GetAbsentMenteesNamesFixture
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
    fun `should return absent mentee names for the requested week`() {
        val case = GetAbsentMenteesNamesFixture.returnsAbsentMenteeNamesForRequestedWeek
        startTestKoin(case)

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(case.requestWeek))

        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()).containsExactlyElementsIn(case.expectedNames)
    }

    @Test
    fun `should return empty list when no mentees are absent in the requested week`() {
        val case = GetAbsentMenteesNamesFixture.returnsEmptyListWhenNoAbsencesExistInRequestedWeek
        startTestKoin(case)

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(case.requestWeek))

        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()).containsExactlyElementsIn(case.expectedNames)
    }

    @Test
    fun `should fail when week number is not positive`() {
        val case = GetAbsentMenteesNamesFixture.failsWhenWeekNumberIsNotPositive
        startTestKoin(case)

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(case.requestWeek))

        assertTrue(result.isFailure)
        assertIs<ValidationExeption.ValueOutOfRangeExeption>(result.exceptionOrNull())
        Truth.assertThat(result.exceptionOrNull()?.message)
            .isEqualTo(case.expectedErrorMessage ?: WeekNumberValidator.Companion.NON_POSITIVE_ERROR)
    }

    @Test
    fun `should ignore absences for other weeks`() {
        val case = GetAbsentMenteesNamesFixture.ignoresAbsencesFromOtherWeeks
        startTestKoin(case)

        val result = resolve<GetAbsentMenteesNamesUseCase>()(WeekNumberRequest(case.requestWeek))

        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()).containsExactlyElementsIn(case.expectedNames)
    }
}