package domain.usecase

import domain.model.exception.ValidationException.DataNotFoundException
import domain.model.request.ProjectIdRequest
import domain.model.entity.Team
import domain.repository.ProjectRepo
import domain.repository.TeamRepo

class FindTeamsWorkingOnProjectUseCase(
    private val projectRepo: ProjectRepo,
    private val teamRepo: TeamRepo
) {

    operator fun invoke(request: ProjectIdRequest): Result<List<Team>> {
        val projects = projectRepo.getAll().getOrElse { return Result.failure(it) }
        val teams = teamRepo.getAll().getOrElse { return Result.failure(it) }

        val teamIds = projects
            .asSequence()
            .filter { it.id == request.id }
            .map { it.teamId }
            .toSet()

        if (teamIds.isEmpty()) {
            return Result.failure(DataNotFoundException(PROJECT_NOT_FOUND))
        }

        return Result.success(
            teams.filter { it.id in teamIds }
        )
    }

    companion object {
        private const val PROJECT_NOT_FOUND = "Project not found"
    }
}
