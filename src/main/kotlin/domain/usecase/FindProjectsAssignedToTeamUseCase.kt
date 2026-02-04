package domain.usecase

import domain.model.Project
import domain.repository.ProjectRepo

class FindProjectsAssignedToTeamUseCase(
    private val projectRepo: ProjectRepo
) {

    operator fun invoke(teamId: String): List<Project> {

        require(teamId.isNotBlank()) { "Team ID cannot be blank" }

        return projectRepo.getByTeamId(teamId)
    }
}
