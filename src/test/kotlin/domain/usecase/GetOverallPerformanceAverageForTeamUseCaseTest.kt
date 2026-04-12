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
import kotlin.test.assertTrue

@DisplayName("GetOverallPerformanceAverageForTeamUseCase")
class GetOverallPerformanceAverageForTeamUseCaseTest : BaseKoinTest() {

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `returns success with overall performance average when requested team has scored mentees`() {
        TestDataFactory.reset()

        val result = resolve<GetOverallPerformanceAverageForTeamUseCase>()(TeamIdRequest("alpha"))

        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()).isEqualTo(55.0)
    }

    @Test
    fun `returns failure with DataNotFoundExeption when requested team has no mentees`() {
        TestDataFactory.reset()
        TestDataFactory.currentTeams = listOf( TestDataFactory.team(id = "gamma", name = "Gamma", mentorLead = "X"))

        val result = resolve<GetOverallPerformanceAverageForTeamUseCase>()(TeamIdRequest("gamma"))

        assertTrue(result.isFailure)
        assertIs<ValidationExeption.DataNotFoundExeption>(result.exceptionOrNull())
        assertThat(result.exceptionOrNull()?.message).isEqualTo("No mentees found for this team")
    }


    @Test
    fun `returns success with zero when requested team mentees have no submissions`() {
        TestDataFactory.reset()

        TestDataFactory.currentPerformances = emptyList()

        val result = resolve<GetOverallPerformanceAverageForTeamUseCase>()(TeamIdRequest("alpha"))

        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()).isEqualTo(0.0)
    }

}

