package domain.usecase.tdd

import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType
import domain.usecase.GetTopPerformingMenteesBySubmissionTypeUseCase
import org.junit.jupiter.api.DisplayName
import support.Fixture
import support.fake.FakeMenteeRepo
import support.fake.FakePerformanceRepo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("GetTopPerformingMenteesBySubmissionTypeUseCase")
class GetTopPerformingMenteesBySubmissionTypeUseCaseTest {

    @Test
    fun `returns top performing mentee and ignores negative scores`() {
        val useCase = GetTopPerformingMenteesBySubmissionTypeUseCase(
           FakePerformanceRepo(
               Fixture.performanceSubmissionList()),
            FakeMenteeRepo(
                 Fixture.menteeList())
        )
        // When
        val result = useCase(SubmissionType.TASK)
        assertTrue(result.isSuccess)
        assertEquals("m01", result.getOrNull()?.id)
    }

    @Test
    fun `returns the first mentee when scores are tied`() {
        // Given
        val useCase = GetTopPerformingMenteesBySubmissionTypeUseCase(
            FakePerformanceRepo(
                Fixture.performanceSubmissionList()),
           FakeMenteeRepo(
               Fixture.menteeList())
        )
        // When
        val result = useCase(SubmissionType.TASK)

        // Then
        assertTrue(result.isSuccess)
        assertEquals("m01", result.getOrNull()?.id)
    }

    @Test
    fun `returns null when no submissions match the required type`() {
        // Given
        val useCase = GetTopPerformingMenteesBySubmissionTypeUseCase(
           FakePerformanceRepo(
                emptyList<PerformanceSubmission>()
            ),
            FakeMenteeRepo(Fixture.menteeList())
        )
        // When
        val result = useCase(SubmissionType.TASK)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(null, result.getOrNull())
    }
}