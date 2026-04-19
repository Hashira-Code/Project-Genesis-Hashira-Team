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

@DisplayName("FindTeamWorkingOnProjectUseCase")
class FindTeamWorkingOnProjectUseCaseTest : BaseKoinTest() {

    private val findTeamWorkingOnProject: FindTeamWorkingOnProjectUseCase by lazy { resolve() }

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `returns success with team when project and assigned team exist`() {
        // Given: default data where project "p01" is assigned to team "alpha"
        val expectedTeam = TestDataFactory.currentTeams.first { it.id == "alpha" }

        // When: finding the team working on project "p01"
        val result = findTeamWorkingOnProject(ProjectIdRequest("p01"))

        // Then: the result should be success and match the expected team
        assertThat(result.getOrThrow()).isEqualTo(expectedTeam)
    }

    @Test
    fun `returns failure with DataNotFoundExeption when project does not exist`() {
        // Given: no projects exist
        TestDataFactory.currentProjects = emptyList()

        // When: finding the team working on a non-existent project
        val result = findTeamWorkingOnProject(ProjectIdRequest("p01"))

        // Then: the result should be failure with DataNotFoundExeption
        assertIs<ValidationExeption.DataNotFoundExeption>(result.exceptionOrNull())
    }

    @Test
    fun `returns failure with DataNotFoundExeption when assigned team does not exist`() {
        // Given: project "p01" is assigned to a team that does not exist
        TestDataFactory.currentProjects = listOf(
            TestDataFactory.project(id = "p01", name = "Hashira", teamId = "hashira"))

        // When: finding the team working on project "p01"
        val result = findTeamWorkingOnProject(ProjectIdRequest("p01"))

        // Then: the result should be failure with DataNotFoundExeption
        assertIs<ValidationExeption.DataNotFoundExeption>(result.exceptionOrNull())
    }
}
