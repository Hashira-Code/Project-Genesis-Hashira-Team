package domain.usecase

import com.google.common.truth.Truth.assertThat
import data.BaseKoinTest
import data.fixture.TestDataFactory
import di.testModule
import domain.model.exception.ValidationExeption
import domain.model.request.ProjectIdRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

@DisplayName("FindTeamWorkingOnProjectUseCase")
class FindTeamWorkingOnProjectUseCaseTest : BaseKoinTest() {

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `returns success with team when project and assigned team exist`() {
        TestDataFactory.reset()

        val expectedTeam = TestDataFactory.currentTeams.first { it.id == "alpha" }

        val result = resolve<FindTeamWorkingOnProjectUseCase>()(ProjectIdRequest("p01"))

        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()).isEqualTo(expectedTeam)
    }

    @Test
    fun `returns failure with DataNotFoundExeption when project does not exist`() {
        TestDataFactory.reset()
        TestDataFactory.currentProjects = emptyList()

        val result = resolve<FindTeamWorkingOnProjectUseCase>()(ProjectIdRequest("p01"))

        assertTrue(result.isFailure)
        assertIs<ValidationExeption.DataNotFoundExeption>(result.exceptionOrNull())
    }

    @Test
    fun `returns failure with DataNotFoundExeption when assigned team does not exist`() {
        TestDataFactory.reset()

        TestDataFactory.currentProjects = listOf(
            TestDataFactory.project(id = "p01", name = "Hashira", teamId = "hashira"))

        val result = resolve<FindTeamWorkingOnProjectUseCase>()(ProjectIdRequest("p01"))

        assertTrue(result.isFailure)
        assertIs<ValidationExeption.DataNotFoundExeption>(result.exceptionOrNull())
    }
}