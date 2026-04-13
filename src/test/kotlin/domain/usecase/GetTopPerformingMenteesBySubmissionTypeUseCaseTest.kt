package domain.usecase

import com.google.common.truth.Truth
import data.BaseKoinTest
import org.junit.jupiter.api.DisplayName
import data.fixture.TestDataFactory
import kotlin.test.Test
import kotlin.test.assertTrue
import domain.model.entity.SubmissionType
import di.testModule

@DisplayName("GetTopPerformingMenteesBySubmissionTypeUseCase")
class GetTopPerformingMenteesBySubmissionTypeUseCaseTest : BaseKoinTest(){
    private val getTopPerformingMenteesBySubmissionType: GetTopPerformingMenteesBySubmissionTypeUseCase by lazy { resolve() }

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `returns top performing mentee and ignores negative scores`() {

        // Given: mentees with various scores, including negative values that should be ignored
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 95.0),
            TestDataFactory.submission("s02", "m02", SubmissionType.TASK, -10.0)
        )

        // When: retrieving the top performer for a specific submission type
        val result = getTopPerformingMenteesBySubmissionType(SubmissionType.TASK)

        // Then: the result should be success and return the mentee with the highest valid score
        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()?.id).isEqualTo("m01")
    }

    @Test
    fun `returns the first mentee when scores are tied`() {

        // Given: multiple mentees having the same highest score for a submission type
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 90.0),
            TestDataFactory.submission("s02", "m02", SubmissionType.TASK, 90.0)
        )

        // When: retrieving the top performer during a score tie
        val result = getTopPerformingMenteesBySubmissionType(SubmissionType.TASK)

        // Then: the result should return the first mentee encountered in the list
        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()?.id).isEqualTo("m01")
    }

    @Test
    fun `returns null when no submissions match the required type`() {
        // Given: a repository where no submissions match the requested submission type
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 95.0)
        )

        // When: attempting to find the top performer for that missing type
        val result = getTopPerformingMenteesBySubmissionType(SubmissionType.BOOK_CLUB)

        // Then: the result should be success but the contained data should be null
        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()).isNull()
    }
}