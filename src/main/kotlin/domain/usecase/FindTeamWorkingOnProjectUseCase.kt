package domain.usecase

import domain.model.entity.Team
import domain.model.exception.ValidationException.DataNotFoundException
import domain.model.request.ProjectIdRequest
import domain.repository.ProjectRepo
import domain.repository.TeamRepo

class FindTeamWorkingOnProjectUseCase(
    private val projectRepo: ProjectRepo,
    private val teamRepo: TeamRepo
) {
    operator fun invoke(request: ProjectIdRequest): Result<Team> {
        val projects = projectRepo.getAll().getOrElse {
           return Result.failure(it)
        }

        val project = projects.find { it.id == request.id }
            ?: return Result.failure(DataNotFoundException(PROJECT_NOT_FOUND_MESSAGE))

        val team = teamRepo.getById(project.teamId).getOrElse {
            return Result.failure(it)
        } ?: return Result.failure(DataNotFoundException(TEAM_NOT_FOUND_MESSAGE))

        return Result.success(team)
    }

    private companion object {
        const val PROJECT_NOT_FOUND_MESSAGE = "Project not found"
        const val TEAM_NOT_FOUND_MESSAGE = "Team not found"
    }
}

