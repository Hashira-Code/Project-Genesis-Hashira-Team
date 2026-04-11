package domain.usecase

import com.google.common.truth.Truth
import di.defaultTestModules
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.context.stopKoin
import data.BaseKoinTest
import data.fixture.TestDataFactory
import domain.model.entity.AttendanceStatus
import domain.model.entity.SubmissionType
import domain.model.entity.TeamHealthStatus
import kotlin.test.assertTrue
import org.koin.core.context.startKoin

@DisplayName("EvaluateTeamHealthUseCase")
class EvaluateTeamHealthUseCaseTest: BaseKoinTest()   {

    private fun startTestKoin() {
        stopKoin()
        startKoin {
            modules(defaultTestModules)
        }
    }

    @Test
    fun `should return EXCELLENT when both performance and attendance are above thresholds`() {

        // Given: a team with high performance (90, 88, 92) and high attendance (all present)
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m03", 1, AttendanceStatus.PRESENT)
        )
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 90.0),
            TestDataFactory.submission("s02", "m02", SubmissionType.TASK, 88.0),
            TestDataFactory.submission("s03", "m03", SubmissionType.TASK, 92.0)
        )
        startTestKoin()

        // When: evaluating the team health status
        val result = resolve<EvaluateTeamHealthUseCase>()()

        // Then: the result should be success and the status for Alpha Team should be EXCELLENT
        assertTrue(result.isSuccess)
        val report = result.getOrNull()
        Truth.assertThat(report).isNotNull()
        Truth.assertThat(report!!["Alpha Team"]).isEqualTo(TeamHealthStatus.EXCELLENT)

    }

    @Test
    fun `should return GOOD when performance is average and attendance is acceptable`() {

        // Given: a team with average performance (70, 68, 65) and acceptable attendance (one absent)
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m03", 1, AttendanceStatus.ABSENT)
        )
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 70.0),
            TestDataFactory.submission("s02", "m02", SubmissionType.TASK, 68.0),
            TestDataFactory.submission("s03", "m03", SubmissionType.TASK, 65.0)
        )
        startTestKoin()

        // When: evaluating the team health status
        val result = resolve<EvaluateTeamHealthUseCase>()()

        // Then: the result should be success and the status for Alpha Team should be GOOD
        assertTrue(result.isSuccess)
        val report = result.getOrNull()
        Truth.assertThat(report!!["Alpha Team"]).isEqualTo(TeamHealthStatus.GOOD)

    }

    @Test
    fun `should return NEEDS_ATTENTION when performance falls below threshold`() {

        // Given: a team with low performance (45, 50, 40) and poor attendance (two absent)
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m03", 1, AttendanceStatus.ABSENT)
        )
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 45.0),
            TestDataFactory.submission("s02", "m02", SubmissionType.TASK, 50.0),
            TestDataFactory.submission("s03", "m03", SubmissionType.TASK, 40.0)
        )
        startTestKoin()

        // When: evaluating the team health status
        val result = resolve<EvaluateTeamHealthUseCase>()()

        // Then: the result should be success and the status for Alpha Team should be NEEDS_ATTENTION
        assertTrue(result.isSuccess)
        val report = result.getOrNull()
        Truth.assertThat(report!!["Alpha Team"]).isEqualTo(TeamHealthStatus.NEEDS_ATTENTION)
    }
}