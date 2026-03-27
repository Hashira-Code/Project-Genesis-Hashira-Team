package domain.usecase

import domain.model.entity.Team
import domain.model.request.ProjectIdRequest
import domain.repository.ProjectRepo
import domain.repository.TeamRepo
import domain.model.exception.ValidationException.DataNotFoundException

class FindTeamWorkingOnProjectUseCase(
    private val projectRepo: ProjectRepo,
    private val teamRepo: TeamRepo
) {
    operator fun invoke(request: ProjectIdRequest): Result<Team> {
        val projects = projectRepo.getAll().getOrElse {
            return Result.failure(it)
        }

        val project = projects.find { it.id == request.id }
            ?: return Result.failure(DataNotFoundException("Project not found"))
        TODO("Handle existing project case after writing the next failing test")




    }
}
