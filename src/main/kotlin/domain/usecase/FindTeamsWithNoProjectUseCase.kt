package domain.usecase

import domain.model.entity.Project
import domain.model.entity.Team
import domain.repository.ProjectRepo
import domain.repository.TeamRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class FindTeamsWithNoProjectUseCase(
    private val teamRepo: TeamRepo,
    private val projectRepo: ProjectRepo
) {
    suspend operator fun invoke(): Result<List<Team>> = coroutineScope {
        val teamsDeferred = async { teamRepo.getAll() }
        val projectsDeferred = async { projectRepo.getAll() }

        val teams = teamsDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val projects = projectsDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }

        if (teams.isEmpty()) return@coroutineScope Result.success(emptyList())

        val teamIdsWithProjects = extractTeamIdsWithProjects(projects)
        val result = filterTeamsWithoutProjects(teams, teamIdsWithProjects)

        Result.success(result)
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
