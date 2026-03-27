package domain.usecase

import domain.model.entity.Project
import domain.model.entity.Team
import domain.model.exception.ValidationException
import domain.model.request.ProjectIdRequest
import org.junit.jupiter.api.DisplayName
import testsupport.fake.repo.FakeProjectRepo
import testsupport.fake.repo.FakeTeamRepo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@DisplayName("FindTeamWorkingOnProjectUseCase")
class FindTeamWorkingOnProjectUseCaseTest {

    @Test
    fun `returns team when project and assigned team both exist`() {
        // Given: a project exists and its assigned team also exists
        val team = createTeam()
        val useCase = FindTeamWorkingOnProjectUseCase(
            projectRepo = FakeProjectRepo(listOf(createProject())),
            teamRepo = FakeTeamRepo(listOf(team))
        )
        //When: searching for the team working on an existing project
        val result = useCase(ProjectIdRequest("p01"))

        // Then: the use case should succeed and return the assigned team
        assertTrue(result.isSuccess)
        assertEquals(team, result.getOrNull())
    }

    @Test
    fun `returns failure when project does not exist`() {
        // Given: there are no projects in the project repository
        val useCase = FindTeamWorkingOnProjectUseCase(
            projectRepo = FakeProjectRepo(emptyList()),
            teamRepo = FakeTeamRepo(emptyList())
        )

        // When: searching for the team working on a project that does not exist
        val result = useCase(ProjectIdRequest("p01"))

        // Then: the use case should return failure
        assertTrue(result.isFailure)
        assertIs<ValidationException.DataNotFoundException>(result.exceptionOrNull())
    }

    private fun createProject(
        id: String = "p01",
        name: String = "Helios Initiative",
        teamId: String = "alpha"
    ) = Project.create(id, name, teamId)

    private fun createTeam(
        id: String = "alpha",
        name: String = "Alpha Team",
        mentorLead: String = "Sarah"
    ) = Team.create(id, name, mentorLead)
}

