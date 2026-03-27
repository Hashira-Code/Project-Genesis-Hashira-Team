package domain.usecase

import domain.model.entity.Team
import domain.model.request.ProjectIdRequest
import domain.repository.ProjectRepo
import domain.repository.TeamRepo

class FindTeamWorkingOnProjectUseCase(
    private val projectRepo: ProjectRepo,
    private val teamRepo: TeamRepo
) {
    operator fun invoke(request: ProjectIdRequest): Result<Team> {
        val project = projectRepo.getAll().getOrElse {
            return Result.failure(it)
        }.find { it.id == request.id }
            ?: TODO("Handle missing project after writing the next failing test")

        val team = teamRepo.getById(project.teamId).getOrElse {
            return Result.failure(it)
        } ?: TODO("Handle missing team after writing the next failing test")

        return Result.success(team)
    }
}

