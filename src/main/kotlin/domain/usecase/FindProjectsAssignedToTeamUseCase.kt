package domain.usecase

import domain.model.Project
import domain.repository.ProjectRepo
import domain.request.FindProjectsAssignedToTeamRequest
import domain.validation.TeamIdValidator

class FindProjectsAssignedToTeamUseCase(
    private val projectRepo: ProjectRepo,
    private val teamIdValidator: TeamIdValidator
) {
    operator fun invoke(request: FindProjectsAssignedToTeamRequest): List<Project> {
        teamIdValidator.validate(request.teamId)
            .onFailure { throw it }

        return projectRepo.getByTeamId(request.teamId)
    }
}
