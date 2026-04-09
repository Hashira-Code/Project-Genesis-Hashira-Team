package domain.usecase

import domain.model.exception.ValidationExeption
import domain.model.request.ProjectIdRequest
import org.junit.jupiter.api.DisplayName
import data.fake.FakeProjectRepo
import data.fake.FakeTeamRepo
import data.fixture.TestDataFactory.defaultProjects
import data.fixture.TestDataFactory.defaultTeams
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@DisplayName("FindTeamWorkingOnProjectUseCase")
class FindTeamWorkingOnProjectUseCaseTest {

    @Test
    fun `returns success with team when project and assigned team exist`() {
        // Given: a project exists and its assigned team also exists
        val teams = defaultTeams()
        val expectedTeam = teams.first()
        val useCase = FindTeamWorkingOnProjectUseCase(
            projectRepo = FakeProjectRepo(defaultProjects()),
            teamRepo = FakeTeamRepo(teams)
        )

        // When: searching for the team working on an existing project
        val result = useCase(ProjectIdRequest("p01"))

        // Then: the use case should succeed and return the assigned team
        assertTrue(result.isSuccess)
        assertEquals(expectedTeam, result.getOrNull())
    }

    @Test
    fun `returns failure with DataNotFoundExeption when project does not exist`() {
        // Given: there are no projects in the project repository
        val useCase = FindTeamWorkingOnProjectUseCase(
            projectRepo = FakeProjectRepo(emptyList()),
            teamRepo = FakeTeamRepo(emptyList())
        )

        // When: searching for the team working on a project that does not exist
        val result = useCase(ProjectIdRequest("p01"))

        // Then: the use case should return failure
        assertTrue(result.isFailure)
        assertIs<ValidationExeption.DataNotFoundExeption>(result.exceptionOrNull())
    }

    @Test
    fun `returns failure with DataNotFoundExeption when assigned team does not exist`() {
        // Given: a project exists, but its assigned team does not exist
        val useCase = FindTeamWorkingOnProjectUseCase(
            projectRepo = FakeProjectRepo(defaultProjects()),
            teamRepo = FakeTeamRepo(emptyList())
        )

        // When: searching for the team working on an existing project
        val result = useCase(ProjectIdRequest("p01"))

        // Then: the use case should return failure
        assertTrue(result.isFailure)
        assertIs<ValidationExeption.DataNotFoundExeption>(result.exceptionOrNull())
    }
}
