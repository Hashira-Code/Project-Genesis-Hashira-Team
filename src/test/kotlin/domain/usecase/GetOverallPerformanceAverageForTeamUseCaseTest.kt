package domain.usecase

import com.google.common.truth.Truth.assertThat
import domain.model.request.TeamIdRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import data.BaseKoinTest
import data.fixture.TestDataFactory
import di.testModule
import domain.model.exception.ValidationExeption
import kotlin.test.assertIs

@DisplayName("GetOverallPerformanceAverageForTeamUseCase")
class GetOverallPerformanceAverageForTeamUseCaseTest : BaseKoinTest() {

    private val getOverallPerformanceAverageForTeamUseCase: GetOverallPerformanceAverageForTeamUseCase by lazy { resolve() }

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `returns success with overall performance average when requested team has scored mentees`() {
        // Given: default data where team "alpha" has mentees with submissions

        // When: getting the overall performance average for team "alpha"
        val result = getOverallPerformanceAverageForTeamUseCase(TeamIdRequest("alpha"))

        // Then: the result should be success and match the expected average
        assertThat(result.getOrThrow()).isEqualTo(55.0)
    }

    @Test
    fun `returns failure with DataNotFoundExeption when requested team has no mentees`() {
        // Given: team "gamma" exists but has no mentees
        TestDataFactory.currentTeams = listOf(TestDataFactory.team(id = "gamma", name = "Gamma", mentorLead = "X"))

        // When: getting the overall performance average for team "gamma"
        val result = getOverallPerformanceAverageForTeamUseCase(TeamIdRequest("gamma"))

        // Then: the result should be failure with DataNotFoundExeption
        assertIs<ValidationExeption.DataNotFoundExeption>(result.exceptionOrNull())
    }

    @Test
    fun `returns success with zero when requested team mentees have no submissions`() {
        // Given: team "alpha" has mentees but no performance submissions
        TestDataFactory.currentPerformances = emptyList()

        // When: getting the overall performance average for team "alpha"
        val result = getOverallPerformanceAverageForTeamUseCase(TeamIdRequest("alpha"))

        // Then: the result should be success with a score of 0.0
        assertThat(result.getOrThrow()).isEqualTo(0.0)
    }

}
