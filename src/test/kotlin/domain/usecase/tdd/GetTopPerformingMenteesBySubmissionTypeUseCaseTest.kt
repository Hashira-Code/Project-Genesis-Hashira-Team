package domain.usecase.tdd

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType
import domain.usecase.GetTopPerformingMenteesBySubmissionTypeUseCase
import org.junit.jupiter.api.DisplayName
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
            performanceRepo = FakePerformanceRepo(
                listOf(createSubmission())
            ),
            menteeRepo = FakeMenteeRepo(
                listOf(createMentee())
            )
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
            performanceRepo = FakePerformanceRepo(
                listOf(createSubmission())
            ),
            menteeRepo = FakeMenteeRepo(
                listOf(createMentee())
            )
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
            performanceRepo = FakePerformanceRepo(
                listOf(createSubmission(type = SubmissionType.WORKSHOP))
            ),
            menteeRepo = FakeMenteeRepo(listOf(createMentee()))
        )
        // When
        val result = useCase(SubmissionType.TASK)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(null, result.getOrNull())
    }



    private fun createMentee(
        id: String = "m01",
        name: String = "Aisha",
        teamId: String = "alpha"
    ) = Mentee.Companion.create(id, name, teamId)

    private fun createSubmission(
        id: String = "s01",
        menteeId: String = "m01",
        type: SubmissionType = SubmissionType.TASK,
        score: Double = 90.0
    ) = PerformanceSubmission(id, menteeId, type, score)
}