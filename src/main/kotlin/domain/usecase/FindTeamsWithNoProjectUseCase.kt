package domain.usecase

import domain.model.entity.Project
import domain.model.entity.Team
import domain.repository.ProjectRepo
import domain.repository.TeamRepo

class FindTeamsWithNoProjectUseCase(
    private val teamRepo: TeamRepo,
    private val projectRepo: ProjectRepo
) {
    operator fun invoke(): Result<List<Team>> {
        val teams = teamRepo.getAll().getOrElse { return Result.failure(it) }
        val projects = projectRepo.getAll().getOrElse { return Result.failure(it) }

        if (teams.isEmpty()) return Result.success(emptyList())

        val teamIdsWithProjects = extractTeamIdsWithProjects(projects)
        val result = filterTeamsWithoutProjects(teams, teamIdsWithProjects)

        return Result.success(result)
    }

    private fun extractTeamIdsWithProjects(projects: List<Project>): Set<String> {
        return projects
            .asSequence()
            .map { it.teamId }
            .toSet()
    }

    private fun filterTeamsWithoutProjects(
        teams: List<Team>,
        teamIdsWithProjects: Set<String>
    ): List<Team> {
        return teams.filter { it.id !in teamIdsWithProjects }
    }
}
