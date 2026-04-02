package domain.usecase.unit.test

import com.google.common.truth.Truth.assertThat
import domain.model.entity.Attendance
import domain.model.entity.PerformanceSubmission
import domain.model.entity.TeamHealthStatus
import domain.usecase.EvaluateTeamHealthUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import domain.repository.AttendanceRepo
import domain.repository.PerformanceRepo
import support.Fixture
import support.fake.FakeAttendanceRepo
import support.fake.FakePerformanceRepo
import testModules
import kotlin.test.assertTrue

@DisplayName("EvaluateTeamHealthUseCase")
class EvaluateTeamHealthUseCaseTest : KoinComponent {

    private val evaluateTeamHealth by inject<EvaluateTeamHealthUseCase>()

    private fun startTestKoin(
        attendance: List<Attendance> = Fixture.attendanceList(),
        performance: List<PerformanceSubmission> = Fixture.performanceSubmissionList()
    ) {
        stopKoin()
        startKoin {
            allowOverride(true)
            modules(
                testModules,
                module {
                    single<AttendanceRepo> { FakeAttendanceRepo(attendance) }
                    single<PerformanceRepo> { FakePerformanceRepo(performance) }
                    single { EvaluateTeamHealthUseCase(get(), get(), get(), get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should return EXCELLENT when both performance and attendance are above thresholds`() {
        startTestKoin(
            Fixture.perfectAttendanceList(),
             Fixture.performanceSubmissionList()
        )
        val result = evaluateTeamHealth()
        assertTrue(result.isSuccess)
        val report = result.getOrNull()
        assertThat(report).isNotNull()
        assertThat(report!!["Alpha Team"]).isEqualTo(TeamHealthStatus.EXCELLENT)
    }

    @Test
    fun `should return GOOD when performance is average and attendance is acceptable`() {
        startTestKoin(
            Fixture.perfectAttendanceList(),
             Fixture.averagePerformanceSubmissionList()
        )
        val evaluateTeamHealth = getKoin().get<EvaluateTeamHealthUseCase>()
        val result = evaluateTeamHealth()
        assertTrue(result.isSuccess)
        val report = result.getOrNull()
        assertThat(report!!["Alpha Team"]).isEqualTo(TeamHealthStatus.GOOD)
    }

    @Test
    fun `should return NEEDS_ATTENTION when performance falls below threshold`() {
        startTestKoin(
             Fixture.lowAttendanceList(),
             Fixture.poorPerformanceSubmissionList()
        )
        val evaluateTeamHealth = getKoin().get<EvaluateTeamHealthUseCase>()
        val result = evaluateTeamHealth()
        assertTrue(result.isSuccess)
        val report = result.getOrNull()
        assertThat(report!!["Alpha Team"]).isEqualTo(TeamHealthStatus.NEEDS_ATTENTION)
    }
}
