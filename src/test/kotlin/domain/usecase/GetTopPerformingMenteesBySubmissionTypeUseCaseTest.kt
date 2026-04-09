package domain.usecase

import di.defaultTestModules
import org.junit.jupiter.api.DisplayName
import data.BaseKoinTest
import data.fake.FakeMenteeRepo
import data.fake.FakePerformanceRepo
import data.fixture.GetTopPerformingMenteesBySubmissionTypeFixture
import kotlin.test.Test
import org.koin.core.context.stopKoin
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("GetTopPerformingMenteesBySubmissionTypeUseCase")
class GetTopPerformingMenteesBySubmissionTypeUseCaseTest : BaseKoinTest(){
    private fun startTestKoin(case: GetTopPerformingMenteesBySubmissionTypeFixture.Case) {
        stopKoin()
        startKoinWith(
            *defaultTestModules(
                performances = case.submissions,
                mentees = case.mentees
            )
        )
    }

    @Test
    fun `returns top performing mentee and ignores negative scores`() {

        // Given: mentees with various scores, including negative values that should be ignored
        val case = GetTopPerformingMenteesBySubmissionTypeFixture.highestValidTaskScoreWins
        val getTopPerformingMenteesBySubmissionTypeUseCase = GetTopPerformingMenteesBySubmissionTypeUseCase(
            FakePerformanceRepo(case.submissions),
            FakeMenteeRepo(case.mentees))

        // When: retrieving the top performer for a specific submission type
        val result = getTopPerformingMenteesBySubmissionTypeUseCase(case.submissionType)

        // Then: the result should be success and return the mentee with the highest valid score
        assertTrue(result.isSuccess)
        assertEquals(case.expectedMenteeId, result.getOrNull()?.id)
    }

    @Test
    fun `returns the first mentee when scores are tied`() {

        // Given: multiple mentees having the same highest score for a submission type
        val case = GetTopPerformingMenteesBySubmissionTypeFixture.tiedScoresReturnFirstEncounteredMentee
        val getTopPerformingMenteesBySubmissionTypeUseCase = GetTopPerformingMenteesBySubmissionTypeUseCase(
            FakePerformanceRepo(case.submissions),
            FakeMenteeRepo(case.mentees))

        // When: retrieving the top performer during a score tie
        val result = getTopPerformingMenteesBySubmissionTypeUseCase(case.submissionType)

        // Then: the result should return the first mentee encountered in the list
        assertTrue(result.isSuccess)
        assertEquals(case.expectedMenteeId, result.getOrNull()?.id)
    }

    @Test
    fun `returns null when no submissions match the required type`() {
        // Given: a repository where no submissions match the requested submission type
        val case = GetTopPerformingMenteesBySubmissionTypeFixture.noMatchingSubmissionType
        val getTopPerformingMenteesBySubmissionTypeUseCase = GetTopPerformingMenteesBySubmissionTypeUseCase(
            FakePerformanceRepo(case.submissions),
            FakeMenteeRepo(case.mentees))

        // When: attempting to find the top performer for that missing type
        val result = getTopPerformingMenteesBySubmissionTypeUseCase(case.submissionType)

        // Then: the result should be success but the contained data should be null
        assertTrue(result.isSuccess)
        assertEquals(case.expectedMenteeId, result.getOrNull()?.id)
    }
}