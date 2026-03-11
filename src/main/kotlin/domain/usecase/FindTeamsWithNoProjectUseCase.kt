package domain.usecase

import domain.model.entity.Team
import domain.repository.ProjectRepo
import domain.repository.TeamRepo

class FindTeamsWithNoProjectUseCase(
    private val teamRepo: TeamRepo,
    private val projectRepo: ProjectRepo
) {

    operator fun invoke(): List<Team> {

        val teams = teamRepo.getAll().getOrThrow()
        val projects = projectRepo.getAll().getOrThrow()

        if (teams.isEmpty()) return emptyList()

        val teamIdsWithProjects = projects
            .asSequence()
            .map { it.teamId }
            .toSet()

        return teams.filter { team ->
            team.id !in teamIdsWithProjects
        }
    }
}


