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
        // Given
        val case = GetTopPerformingMenteesBySubmissionTypeFixture.highestValidTaskScoreWins
        val useCase = GetTopPerformingMenteesBySubmissionTypeUseCase(
            FakePerformanceRepo(case.submissions),
            FakeMenteeRepo(case.mentees)
        )
        // When
        val result = useCase(case.submissionType)
        // Then
        assertTrue(result.isSuccess)
        assertEquals(case.expectedMenteeId, result.getOrNull()?.id)
    }

    @Test
    fun `returns the first mentee when scores are tied`() {
        // Given
        val case = GetTopPerformingMenteesBySubmissionTypeFixture.tiedScoresReturnFirstEncounteredMentee
        val useCase = GetTopPerformingMenteesBySubmissionTypeUseCase(
            FakePerformanceRepo(case.submissions),
            FakeMenteeRepo(case.mentees)
        )
        // When
        val result = useCase(case.submissionType)
        // Then
        assertTrue(result.isSuccess)
        assertEquals(case.expectedMenteeId, result.getOrNull()?.id)
    }

    @Test
    fun `returns null when no submissions match the required type`() {
        // Given
        val case = GetTopPerformingMenteesBySubmissionTypeFixture.noMatchingSubmissionType
        val useCase = GetTopPerformingMenteesBySubmissionTypeUseCase(
            FakePerformanceRepo(case.submissions),
            FakeMenteeRepo(case.mentees)
        )
        // When
        val result = useCase(case.submissionType)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(case.expectedMenteeId, result.getOrNull()?.id)
    }
}