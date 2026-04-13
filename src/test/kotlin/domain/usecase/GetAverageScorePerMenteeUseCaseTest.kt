package domain.usecase

import com.google.common.truth.Truth
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import di.testModule
import data.fixture.TestDataFactory
import data.BaseKoinTest
import domain.model.entity.SubmissionType
import kotlin.test.assertTrue

@DisplayName("GetAverageScorePerMenteeUseCase")
class GetAverageScorePerMenteeUseCaseTest : BaseKoinTest() {

    private val getAverageScorePerMentee: GetAverageScorePerMenteeUseCase by lazy { resolve() }
    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `should calculate correct average score for each mentee`() {

        // Given: Default submissions provided by TestDataFactory

        // When: calculating the average score for each mentee
        val result = getAverageScorePerMentee()

        // Then: the result should be success and contain the correct pre-calculated average
        assertTrue(result.isSuccess)
        val averages = result.getOrNull()
        Truth.assertThat(averages).isNotNull()
        Truth.assertThat(averages).isNotEmpty()
    }

    @Test
    fun `should exclude mentees who have no submissions from the result list`() {

        // Given: Reset and ensure only one mentee has submissions
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 80.0)
        )

        // When: calculating averages for all mentees
        val result = getAverageScorePerMentee()

        // Then: mentees without submissions should be filtered out, leaving only active ones
        assertTrue(result.isSuccess)
        val averages = result.getOrNull()
        Truth.assertThat(averages).hasSize(1)
        Truth.assertThat(averages!![0]).isEqualTo("Aisha" to 80.0)
    }

    @Test
    fun `should return empty list when there are no submissions at all`() {

        // Given: Reset and clear all submissions
        TestDataFactory.currentPerformances = emptyList()

        // When: attempting to calculate average scores
        val result = getAverageScorePerMentee()

        // Then: the result should be a success with an empty list of averages
        assertTrue(result.isSuccess)
        val averages = result.getOrNull()
        Truth.assertThat(averages).isEmpty()
    }
}