package domain.usecase.unit

import com.google.common.truth.Truth
import org.junit.jupiter.api.DisplayName
import support.BaseKoinTest
import support.fixture.GetAverageScorePerMenteeFixture
import org.koin.core.context.stopKoin
import di.defaultTestModules
import domain.usecase.GetAverageScorePerMenteeUseCase
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue


@DisplayName("GetAverageScorePerMenteeUseCase")
class GetAverageScorePerMenteeUseCaseTest : BaseKoinTest() {
    private fun startTestKoin(case: GetAverageScorePerMenteeFixture.Case) {
        stopKoin()
        startKoinWith(
            *defaultTestModules(
                mentees = case.mentees,
                performances = case.submissions
            )
        )
    }

    @Test
    fun `should calculate correct average score for each mentee`() {
        // Given
        val case = GetAverageScorePerMenteeFixture.calculatesAverageScorePerMentee
        startTestKoin(case)
        val useCase = resolve<GetAverageScorePerMenteeUseCase>()

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        val averages = result.getOrNull()
        Truth.assertThat(averages).isNotNull()
        Truth.assertThat(averages).containsExactlyElementsIn(case.expectedAverages)
    }

    @Test
    fun `should exclude mentees who have no submissions from the result list`() {
        // Given
        val case = GetAverageScorePerMenteeFixture.excludesMenteesWithoutSubmissions
        startTestKoin(case)
        val useCase = resolve<GetAverageScorePerMenteeUseCase>()

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        val averages = result.getOrNull()
        Truth.assertThat(averages).hasSize(1)
        Truth.assertThat(averages).containsExactlyElementsIn(case.expectedAverages)
    }

    @Test
    fun `should return empty list when there are no submissions at all`() {
        // Given
        val case = GetAverageScorePerMenteeFixture.returnsEmptyListWhenThereAreNoSubmissions
        startTestKoin(case)
        val useCase = resolve<GetAverageScorePerMenteeUseCase>()

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        val averages = result.getOrNull()
        Truth.assertThat(averages).isEmpty()
    }
}