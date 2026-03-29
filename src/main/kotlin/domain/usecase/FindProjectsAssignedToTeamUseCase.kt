package domain.usecase

import domain.model.entity.Project
import domain.repository.ProjectRepo
import domain.model.request.TeamIdRequest
import domain.model.exception.ValidationException.EntityNotFoundException
import domain.validation.Validator

class FindProjectsAssignedToTeamUseCase(
    private val projectRepo: ProjectRepo,
) {
    operator fun invoke(request: TeamIdRequest): Result<List<Project>> {
        val projects = projectRepo.getByTeamId(request.id).getOrElse {
            return Result.failure(it)
        }
        return validateProjectsList(projects)
    }

    private fun validateProjectsList(projects: List<Project>): Result<List<Project>> {
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