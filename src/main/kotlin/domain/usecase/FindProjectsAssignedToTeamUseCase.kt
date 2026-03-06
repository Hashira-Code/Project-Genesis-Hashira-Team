package domain.usecase

import domain.model.Project
import domain.repository.ProjectRepo
import domain.model.request.TeamIdRequest
import domain.model.exception.ValidationException.EntityNotFoundException
import domain.validation.Validator

class FindProjectsAssignedToTeamUseCase(
    private val projectRepo: ProjectRepo,
    private val teamIdValidator: Validator<String, String>
) {

    operator fun invoke(request: TeamIdRequest): Result<List<Project>> {
        teamIdValidator.validate(request.id)
            .onFailure { return Result.failure(it) }

        val projects = projectRepo.getByTeamId(request.id).getOrThrow()
        return if (projects.isNotEmpty()) {
            Result.success(projects)
        } else {
            Result.failure(EntityNotFoundException(NO_PROJECTS_FOUND_MSG))
        }
    }

    companion object {
        const val NO_PROJECTS_FOUND_MSG = "No projects found for the team"
    }

}
