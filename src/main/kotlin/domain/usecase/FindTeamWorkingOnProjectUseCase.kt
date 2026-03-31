package domain.usecase

import domain.model.entity.Project
import domain.model.entity.Team
import domain.model.exception.ValidationExeption.DataNotFoundExeption
import domain.model.request.ProjectIdRequest
import domain.repository.ProjectRepo
import domain.repository.TeamRepo

class FindTeamWorkingOnProjectUseCase(
    private val projectRepo: ProjectRepo,
    private val teamRepo: TeamRepo
) {
    operator fun invoke(request: ProjectIdRequest): Result<Team> {
        val project = findProjectById(request.id).getOrElse {
            return Result.failure(it)
        }

        val team = teamRepo.getById(project.teamId).getOrElse {
            return Result.failure(it)
        } ?: return Result.failure(DataNotFoundExeption(TEAM_NOT_FOUND_MESSAGE))

        return Result.success(team)
    }

    private fun findProjectById(projectId: String): Result<Project> {
        val projects = projectRepo.getAll().getOrElse {
            return Result.failure(it)
        }

        val project = projects.find { it.id == projectId }
            ?: return Result.failure(DataNotFoundExeption(PROJECT_NOT_FOUND_MESSAGE))

        return Result.success(project)
    }

    private companion object {
        const val PROJECT_NOT_FOUND_MESSAGE = "Project not found"
        const val TEAM_NOT_FOUND_MESSAGE = "Team not found"
    }
}

