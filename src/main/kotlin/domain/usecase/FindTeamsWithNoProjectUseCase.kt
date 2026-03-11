package domain.usecase

import domain.model.entity.Team
import domain.repository.ProjectRepo
import domain.repository.TeamRepo

class FindTeamsWithNoProjectUseCase(
    private val teamRepo: TeamRepo,
    private val projectRepo: ProjectRepo
) {
    operator fun invoke(): Result<List<Team>> {
        val teams = teamRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val projects = projectRepo.getAll().getOrElse {
            return Result.failure(it)
        }

        if (teams.isEmpty()) return Result.success(emptyList())

        val teamIdsWithProjects = projects
            .asSequence()
            .map { it.teamId }
            .toSet()

        val teamsWithoutProjects = teams.filter { team ->
            team.id !in teamIdsWithProjects
        }

        return Result.success(teamsWithoutProjects)
    }
}
