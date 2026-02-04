package domain.usecase

import domain.model.Team
import domain.repository.ProjectRepo
import domain.repository.TeamRepo

class FindTeamsWithNoProjectUseCase(
    private val teamRepo: TeamRepo,
    private val projectRepo: ProjectRepo
) {

    operator fun invoke(): List<Team> {

        val teams = teamRepo.getAll()
        val projects = projectRepo.getAll()

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
