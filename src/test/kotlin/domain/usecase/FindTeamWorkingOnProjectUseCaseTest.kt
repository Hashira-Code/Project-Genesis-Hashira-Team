package domain.usecase

import domain.model.exception.ValidationException
import domain.model.request.ProjectIdRequest
import domain.model.entity.Project
import org.junit.jupiter.api.DisplayName
import testsupport.fake.repo.FakeProjectRepo
import testsupport.fake.repo.FakeTeamRepo
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue


@DisplayName("FindTeamWorkingOnProjectUseCase")
class FindTeamWorkingOnProjectUseCaseTest {
    @Test
    fun `returns DataNotFoundException when project does not exist`() {
        //Given: a use case with no projects in the project repository
        val useCase = FindTeamWorkingOnProjectUseCase(
            projectRepo = FakeProjectRepo(emptyList()),
            teamRepo = FakeTeamRepo(emptyList())
        )
        //When: searching for the team working on a project id that does not exis
        val result = useCase(ProjectIdRequest("p99"))
        //Then: the use case should fail with DataNotFoundException
        assertTrue(result.isFailure)
        assertIs<ValidationException.DataNotFoundException>(result.exceptionOrNull())
    }

    @Test
    fun `returns DataNotFoundException when team assigned to project does not exist`() {
        // Given: a project exists, but its assigned team does not exist in the team repository
        val useCase = FindTeamWorkingOnProjectUseCase(
            projectRepo = FakeProjectRepo(listOf(createProject())),
            teamRepo = FakeTeamRepo(emptyList())
        )

        // When: searching for the team working on an existing project
        val result = useCase(ProjectIdRequest("p01"))

        // Then: the use case should fail with DataNotFoundException
        assertTrue(result.isFailure)
        assertIs<ValidationException.DataNotFoundException>(result.exceptionOrNull())
    }

    private fun createProject(
        id: String = "p01",
        name: String = "Helios Initiative",
        teamId: String = "alpha"

    ) = Project.create(id, name, teamId)
}