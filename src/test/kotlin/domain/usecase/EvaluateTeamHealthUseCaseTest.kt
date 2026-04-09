package domain.usecase

import com.google.common.truth.Truth
import di.defaultTestModules
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.context.stopKoin
import data.BaseKoinTest
import data.fixture.EvaluateTeamHealthFixture
import kotlin.test.assertTrue

@DisplayName("EvaluateTeamHealthUseCase")
class EvaluateTeamHealthUseCaseTest: BaseKoinTest()   {

    private fun startTestKoin(case: EvaluateTeamHealthFixture.Case) {
        stopKoin()
        startKoinWith(
            *defaultTestModules(
                attendances = case.attendance,
                performances = case.performances
            )
        )
    }

    @Test
    fun `should return EXCELLENT when both performance and attendance are above thresholds`() {
        val case = EvaluateTeamHealthFixture.excellent
        startTestKoin(case)
        val result = resolve<EvaluateTeamHealthUseCase>()()
        assertTrue(result.isSuccess)
        val report = result.getOrNull()
        Truth.assertThat(report).isNotNull()
        Truth.assertThat(report!!["Alpha Team"]).isEqualTo(case.expectedAlphaTeamStatus)

    }

    @Test
    fun `should return GOOD when performance is average and attendance is acceptable`() {
        val case = EvaluateTeamHealthFixture.good
        startTestKoin(case)
        val result = resolve<EvaluateTeamHealthUseCase>()()
        assertTrue(result.isSuccess)
        val report = result.getOrNull()
        Truth.assertThat(report!!["Alpha Team"]).isEqualTo(case.expectedAlphaTeamStatus)

    }

    @Test
    fun `should return NEEDS_ATTENTION when performance falls below threshold`() {
        val case = EvaluateTeamHealthFixture.needsAttention
        startTestKoin(case)

        val result = resolve<EvaluateTeamHealthUseCase>()()

        assertTrue(result.isSuccess)
        val report = result.getOrNull()
        Truth.assertThat(report!!["Alpha Team"]).isEqualTo(case.expectedAlphaTeamStatus)
    }
}