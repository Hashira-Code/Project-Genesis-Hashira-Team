package domain.usecase

import domain.model.entity.Project
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

        return TODO("Provide the return value")
    }
}