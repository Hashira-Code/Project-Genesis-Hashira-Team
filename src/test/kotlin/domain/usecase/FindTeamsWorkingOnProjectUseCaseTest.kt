package domain.usecase

import FindTeamWorkingOnProjectUseCase
import domain.model.exception.ValidationException
import domain.model.request.ProjectIdRequest
import org.junit.jupiter.api.DisplayName
import testsupport.fake.repo.FakeProjectRepo
import testsupport.fake.repo.FakeTeamRepo
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

@DisplayName("FindTeamWorkingOnProjectUseCase")
class FindTeamsWorkingOnProjectUseCaseTest {
    @Test
    fun `returns DataNotFoundException when project does not exist`() {
        //Given: a use case with no projects in the project repository
        val useCase = FindTeamWorkingOnProjectUseCase(
            projectRepo = FakeProjectRepo(emptyList()),
            teamRepo = FakeTeamRepo(emptyList())
        )
        //When: searching for the team working on an existing project
        val result = useCase(ProjectIdRequest("p99"))
        //Then: the use case should succeed and return the assigned team
        assertTrue(result.isFailure)
        assertIs<ValidationException.DataNotFoundException>(result.exceptionOrNull())
    }
}