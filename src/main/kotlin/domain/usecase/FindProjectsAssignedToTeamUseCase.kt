package domain.usecase

import domain.model.Project
import domain.repository.ProjectRepo
import domain.validation.TeamIdValidator
import domain.request.TeamIdRequest
class FindProjectsAssignedToTeamUseCase(
    private val projectRepo: ProjectRepo,
    private val teamIdValidator: TeamIdValidator
) {
    operator fun invoke(request: TeamIdRequest): List<Project> {
        val validationResult =
            teamIdValidator.validate(request.id)
        validationResult.onFailure {
            throw it
        }
        return projectRepo.getByTeamId(request.id)
    }
}
