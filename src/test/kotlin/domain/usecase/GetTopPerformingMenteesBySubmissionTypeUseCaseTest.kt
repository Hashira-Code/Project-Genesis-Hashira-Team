package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.SubmissionType
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import io.mockk.mockk
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat
import domain.model.entity.PerformanceSubmission
import io.mockk.every

class GetTopPerformingMenteesBySubmissionTypeUseCaseTest {
    private val performanceRepo = mockk<PerformanceRepo>()
    private val menteeRepo = mockk<MenteeRepo>()
    private val useCase = GetTopPerformingMenteesBySubmissionTypeUseCase(performanceRepo, menteeRepo)

    @Test
    fun `should return top mentee and handle invalid or negative scores from CSV data`() {
        val type = SubmissionType.TASK
        val submissions = listOf(
            PerformanceSubmission("sub001",  "m001", type,  95.0),
            PerformanceSubmission("sub175",  "m088",  type,  -1.0),
            PerformanceSubmission( "sub193",  "m097",  type,  -5.0)
        )
        val mentees = listOf(
            Mentee.create("m001", "Aisha Sharma", "alpha"),
            Mentee.create("m088", "Nolan Gonzales", "victor"),
            Mentee.create("m097", "Clara Hamilton", "x-ray")
        )
        every { performanceRepo.getAll() } returns Result.success(submissions)
        every { menteeRepo.getAll() } returns Result.success(mentees)

        val result = useCase(type)

        assertThat(result.getOrNull()?.id).isEqualTo("m001")
    }

    @Test
    fun `should return the first mentee when scores are tied`() {
        val type = SubmissionType.TASK
        val submissions = listOf(
            PerformanceSubmission( "sub001",  "m001",  type,  95.0),
            PerformanceSubmission("sub009",  "m005",type = type,  95.0)
        )
        val mentees = listOf(
            Mentee.create("m001", "Aisha Sharma", "alpha"),
            Mentee.create("m005", "Emily White", "alpha")
        )

        every { performanceRepo.getAll() } returns Result.success<List<PerformanceSubmission>>(submissions)
        every { menteeRepo.getAll() } returns Result.success<List<Mentee>>(mentees)

        val result = useCase(type)

        assertThat(result.getOrNull()?.id).isEqualTo("m001")
    }

    @Test
    fun `should return null when no submissions exist for the type`() {
        every { performanceRepo.getAll() } returns Result.success(emptyList())
        every { menteeRepo.getAll() } returns Result.success(emptyList())

        val result = useCase(SubmissionType.TASK)

        assertThat(result.getOrNull()).isNull()
    }

    @Test
    fun `should ignore scores of mentees that do not exist in mentee list`() {
        val type = SubmissionType.WORKSHOP
        val submissions = listOf(
            PerformanceSubmission( "sub112", "m999",  type, 100.0)
        )
        every { performanceRepo.getAll() } returns Result.success(submissions)
        every { menteeRepo.getAll() } returns Result.success(emptyList())

        val result = useCase(type)

        assertThat(result.getOrNull()).isNull()
    }
}
