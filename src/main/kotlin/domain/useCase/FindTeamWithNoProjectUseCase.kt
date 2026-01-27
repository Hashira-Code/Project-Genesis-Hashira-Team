package domain.usecase

import domain.repository.ProjectRepo
import domain.model.Project
import domain.model.Team
import domain.repository.TeamRepo

class FindTeamWithNoProjectUseCase (
    private val teamRepo: TeamRepo ,
    private val projectRepo: ProjectRepo

){
    fun execute(teamId: String ) : List<Team> {
        val projects = projectRepo.getAll()
        val teams= teamRepo.getAll()
        return findProjectForTeam(teams,projects)
    }


    private fun  findProjectForTeam( teams : List<Team>,projects : List<Project> ) : List<Team>
    {
        val projectsByTeamId=projects.groupBy {it.teamId }
        return teams.filter { team ->
            team.id !in projectsByTeamId
        }
    }



}



