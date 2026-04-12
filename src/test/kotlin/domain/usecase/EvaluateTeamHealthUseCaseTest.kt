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
class EvaluateTeamHealthUseCaseTest: BaseKoinTest()   {

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `should return EXCELLENT when both performance and attendance are above thresholds`() {

        // Given: Team with default excellent data provided by TestDataFactory
        val evaluateTeamHealth = resolve<EvaluateTeamHealthUseCase>()

        // When: evaluating the team health status
        val result = evaluateTeamHealth()

        // Then: the result should be success and the status for Alpha Team should be EXCELLENT
        assertTrue(result.isSuccess)
        val report = result.getOrNull()
        Truth.assertThat(report).isNotNull()
        Truth.assertThat(report!!["Alpha Team"]).isEqualTo(TeamHealthStatus.EXCELLENT)

    }

    @Test
    fun `should return GOOD when performance is average and attendance is acceptable`() {

        // Given: Reset to defaults and adjust performance to average level
        TestDataFactory.reset()
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 70.0)
        )
        val evaluateTeamHealth = resolve<EvaluateTeamHealthUseCase>()

        // When: evaluating the team health status
        val result = evaluateTeamHealth()

        // Then: the result should be success and the status for Alpha Team should be GOOD
        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()!!["Alpha Team"]).isEqualTo(TeamHealthStatus.GOOD)
    }

    @Test
    fun `should return NEEDS_ATTENTION when performance falls below threshold`() {

        // Given: Reset to defaults and adjust attendance to reflect poor participation
        TestDataFactory.reset()
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT)
        )
        val evaluateTeamHealth = resolve<EvaluateTeamHealthUseCase>()

        // When: evaluating the team health status
        val result = evaluateTeamHealth()

        // Then: the result should be success and the status for Alpha Team should be NEEDS_ATTENTION
        assertTrue(result.isSuccess)
        Truth.assertThat(result.getOrNull()!!["Alpha Team"]).isEqualTo(TeamHealthStatus.NEEDS_ATTENTION)
    }
}