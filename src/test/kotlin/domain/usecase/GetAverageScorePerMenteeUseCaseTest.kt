package domain.usecase

import com.google.common.truth.Truth
import di.defaultTestModules
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.context.stopKoin
import data.fixture.TestDataFactory
import data.BaseKoinTest
import data.fixture.GetAverageScorePerMenteeFixture
import kotlin.test.assertTrue
import org.koin.core.context.startKoin

@DisplayName("GetAverageScorePerMenteeUseCase")
class GetAverageScorePerMenteeUseCaseTest : BaseKoinTest() {
    private fun startTestKoin(case: GetAverageScorePerMenteeFixture.Case) {
        TestDataFactory.currentMentees = case.mentees
        TestDataFactory.currentPerformances = case.submissions

        stopKoin()
        startKoin {
            modules(defaultTestModules)
        }
    }

    @Test
    fun `should calculate correct average score for each mentee`() {

        // Given: a list of mentees with multiple task submissions and scores
        val case = GetAverageScorePerMenteeFixture.calculatesAverageScorePerMentee
        startTestKoin(case)
        val getAverageScorePerMenteeUseCase = resolve<GetAverageScorePerMenteeUseCase>()

        // When: calculating the average score for each mentee
        val result = getAverageScorePerMenteeUseCase()

        // Then: the result should be success and contain the correct pre-calculated average
        assertTrue(result.isSuccess)
        val averages = result.getOrNull()
        Truth.assertThat(averages).isNotNull()
        Truth.assertThat(averages).containsExactlyElementsIn(case.expectedAverages)
    }

    @Test
    fun `should exclude mentees who have no submissions from the result list`() {

        // Given: a mix of mentees where some have submissions and others don't
        val case = GetAverageScorePerMenteeFixture.excludesMenteesWithoutSubmissions
        startTestKoin(case)
        val getAverageScorePerMenteeUseCase = resolve<GetAverageScorePerMenteeUseCase>()

        // When: calculating averages for all mentees
        val result = getAverageScorePerMenteeUseCase()

        // Then: mentees without submissions should be filtered out, leaving only active ones
        assertTrue(result.isSuccess)
        val averages = result.getOrNull()
        Truth.assertThat(averages).hasSize(1)
        Truth.assertThat(averages).containsExactlyElementsIn(case.expectedAverages)
    }

    @Test
    fun `should return empty list when there are no submissions at all`() {

        // Given: a system state where no tasks have been submitted by any mentee
        val case = GetAverageScorePerMenteeFixture.returnsEmptyListWhenThereAreNoSubmissions
        startTestKoin(case)
        val getAverageScorePerMenteeUseCase = resolve<GetAverageScorePerMenteeUseCase>()

        // When: attempting to calculate average scores
        val result = getAverageScorePerMenteeUseCase()

        // Then: the result should be a success with an empty list of averages
        assertTrue(result.isSuccess)
        val averages = result.getOrNull()
        Truth.assertThat(averages).isEmpty()
    }
}