package domain.usecase

import com.google.common.truth.Truth.assertThat
import data.BaseKoinTest
import data.fixture.TestDataFactory
import di.testModule
import domain.model.entity.SubmissionType
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
            "Alpha Team" to 87.5
        ).inOrder()
    }

    @Test
    fun `returns success with zero average for team mentees without submissions`() {
        // Given: Alpha Team has mentees but no performance submissions
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s01", "m03", SubmissionType.TASK, 70.0)
        )

        // When: generating a cross-team performance report
        val result = generateCrossTeamPerformanceReport()

        // Then: Alpha Team should be included with zero average
        assertThat(result.getOrThrow()).contains("Alpha Team" to 0.0)
    }

    @Test
    fun `returns teams with tied averages ordered by team name`() {
        // Given: teams have the same average but are stored in reverse alphabetical order
        TestDataFactory.currentTeams = listOf(
            TestDataFactory.team(id = "beta", name = "Beta Team", mentorLead = "Omar"),
            TestDataFactory.team(id = "alpha", name = "Alpha Team", mentorLead = "Sarah")
        )
        TestDataFactory.currentMentees = listOf(
            TestDataFactory.mentee(id = "m01", name = "Aisha", teamId = "alpha"),
            TestDataFactory.mentee(id = "m03", name = "Lina", teamId = "beta")
        )
        TestDataFactory.currentPerformances = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 90.0),
            TestDataFactory.submission("s02", "m03", SubmissionType.TASK, 90.0)
        )

        // When: generating a cross-team performance report
        val result = generateCrossTeamPerformanceReport()

        // Then: tied teams should be ordered by team name
        assertThat(result.getOrThrow()).containsExactly(
            "Alpha Team" to 90.0,
            "Beta Team" to 90.0
        ).inOrder()
    }
}
