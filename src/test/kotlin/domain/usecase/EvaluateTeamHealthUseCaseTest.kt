package domain.usecase

import com.google.common.truth.Truth
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import data.BaseKoinTest
import data.fixture.TestDataFactory
import di.testModule
import domain.model.entity.AttendanceStatus
import domain.model.entity.SubmissionType
import domain.model.entity.TeamHealthStatus
import kotlin.test.assertTrue

@DisplayName("EvaluateTeamHealthUseCase")
class EvaluateTeamHealthUseCaseTest : BaseKoinTest() {

    private val evaluateTeamHealth: EvaluateTeamHealthUseCase by lazy { resolve() }

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `should return EXCELLENT when both performance and attendance are above thresholds`() {

        // Given: Team with default excellent data provided by TestDataFactory
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s1", "m01", SubmissionType.TASK, 90.0) // 90 > 80
        )
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT) // 100% > 90%
        )
        // When: evaluating the team health status
        val result = evaluateTeamHealth()

        // Then: the result should be success and the status for Alpha Team should be EXCELLENT
        Truth.assertThat(result.getOrNull()!!["Alpha Team"]).isEqualTo(TeamHealthStatus.EXCELLENT)

    }

    @Test
    fun `should return GOOD when performance is average and attendance is acceptable`() {

        // Given: performance and attendance meet the GOOD criteria.
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 70.0)
        )
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT)
        )

        // When: evaluating the team health status
        val result = evaluateTeamHealth()

        // Then: the result should be success and the status for Alpha Team should be GOOD
        Truth.assertThat(result.getOrNull()!!["Alpha Team"]).isEqualTo(TeamHealthStatus.GOOD)
    }

    @Test
    fun `should return NEEDS_ATTENTION when performance falls below threshold`() {

        // Given: adjust attendance to reflect poor participation
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT)
        )

        // When: evaluating the team health status
        val result = evaluateTeamHealth()

        // Then: the result should be success and the status for Alpha Team should be NEEDS_ATTENTION
        Truth.assertThat(result.getOrNull()!!["Alpha Team"]).isEqualTo(TeamHealthStatus.NEEDS_ATTENTION)
    }
}