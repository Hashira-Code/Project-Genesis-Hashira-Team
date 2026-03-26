package domain.usecase

import com.google.common.truth.Truth.assertThat
import domain.model.exception.ValidationException.DataNotFoundException
import domain.model.request.ProjectIdRequest
import org.junit.jupiter.api.Test
import testsupport.fake.repo.FakeProjectRepo
import testsupport.fake.repo.FakeTeamRepo
import domain.model.entity.Project
import domain.model.entity.Team

class FindTeamsWorkingOnProjectUseCaseTest {

    @Test
    fun `returns data not found failure when project id does not exist`() {
        // Given
        val useCase = FindTeamsWorkingOnProjectUseCase(
            projectRepo = FakeProjectRepo(
                projects = listOf(
                    Project.create(id = "p1", name = "Genesis", teamId = "alpha")
                )
            ),
            teamRepo = FakeTeamRepo(
                teams = listOf(
                    Team.create(id = "alpha", name = "Alpha", mentorLead = "Mentor A")
                )
            )
        )

        // When
        val result = useCase(ProjectIdRequest(id = "p9"))

        // Then
        assertThat(result.exceptionOrNull()).isInstanceOf(DataNotFoundException::class.java)
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Project not found")
    }
}
