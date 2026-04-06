package domain.usecase.tdd

import domain.usecase.GetTopPerformingMenteesBySubmissionTypeUseCase
import org.junit.jupiter.api.DisplayName
import support.fake.FakeMenteeRepo
import support.fake.FakePerformanceRepo
import support.fixture.GetTopPerformingMenteesBySubmissionTypeFixture
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("GetTopPerformingMenteesBySubmissionTypeUseCase")
class GetTopPerformingMenteesBySubmissionTypeUseCaseTest {

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