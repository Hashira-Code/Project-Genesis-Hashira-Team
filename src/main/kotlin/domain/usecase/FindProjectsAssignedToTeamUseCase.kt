package domain.usecase

import domain.model.Project
import domain.repository.ProjectRepo
import domain.request.TeamIdRequest
import domain.validation.TeamIdValidator

class FindProjectsAssignedToTeamUseCase(
    private val projectRepo: ProjectRepo,
    private val teamIdValidator: TeamIdValidator
) {
    operator fun invoke(request: TeamIdRequest): List<Project> {
        teamIdValidator.validate(request.id)
            .onFailure { throw it }

        return projectRepo.getByTeamId(request.id)
    }
}
