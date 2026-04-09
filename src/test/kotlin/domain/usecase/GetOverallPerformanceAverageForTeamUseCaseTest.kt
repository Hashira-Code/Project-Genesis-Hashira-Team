package domain.usecase

import com.google.common.truth.Truth.assertThat
import di.defaultTestModules
import domain.model.exception.ValidationExeption
import domain.model.request.TeamIdRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import data.BaseKoinTest
import data.fixture.GetOverallPerformanceAverageForTeamFixture
import kotlin.test.assertIs
import kotlin.test.assertTrue

@DisplayName("GetOverallPerformanceAverageForTeamUseCase")
class GetOverallPerformanceAverageForTeamUseCaseTest : BaseKoinTest() {

    private fun startTestKoin(case: GetOverallPerformanceAverageForTeamFixture.Case) {
        startKoinWith(
            *defaultTestModules(
                mentees = case.mentees,
                performances = case.submissions
            )
        )
    }

    @Test
    fun `returns success with overall performance average when requested team has scored mentees`() {
        val case = GetOverallPerformanceAverageForTeamFixture.teamWithScoredMentees
        startTestKoin(case)

        val result = resolve<GetOverallPerformanceAverageForTeamUseCase>()(TeamIdRequest(case.requestTeamId))

        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()).isEqualTo(case.expectedAverage)
    }

    @Test
    fun `returns failure with DataNotFoundExeption when requested team has no mentees`() {
        val case = GetOverallPerformanceAverageForTeamFixture.teamWithNoMentees
        startTestKoin(case)

        val result = resolve<GetOverallPerformanceAverageForTeamUseCase>()(TeamIdRequest(case.requestTeamId))

        assertTrue(result.isFailure)
        assertIs<ValidationExeption.DataNotFoundExeption>(result.exceptionOrNull())
        assertThat(result.exceptionOrNull()?.message).isEqualTo(case.expectedErrorMessage)
    }

    @Test
    fun `returns success with zero when requested team mentees have no submissions`() {
        val case = GetOverallPerformanceAverageForTeamFixture.teamWithMenteesButNoSubmissions
        startTestKoin(case)

        val result = resolve<GetOverallPerformanceAverageForTeamUseCase>()(TeamIdRequest(case.requestTeamId))

        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()).isEqualTo(case.expectedAverage)
    }
}
