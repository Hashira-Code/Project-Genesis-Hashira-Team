package domain.usecase

import com.google.common.truth.Truth.assertThat
import data.BaseKoinTest
import di.testModule
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("GenerateCrossTeamPerformanceReportUseCase")
class GenerateCrossTeamPerformanceReportUseCaseTest : BaseKoinTest() {

    private val generateCrossTeamPerformanceReport: GenerateCrossTeamPerformanceReportUseCase by lazy { resolve() }

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `returns success with team performance averages ordered from highest to lowest`() {
        // Given: default data where Beta Team average is higher than Alpha Team

        // When: generating a cross-team performance report
        val result = generateCrossTeamPerformanceReport()

        // Then: the report should be ordered by highest average performance
        assertThat(result.getOrThrow()).containsExactly(
            "Beta Team" to 99.0,
            "Alpha Team" to 55.0
        ).inOrder()
    }
}
