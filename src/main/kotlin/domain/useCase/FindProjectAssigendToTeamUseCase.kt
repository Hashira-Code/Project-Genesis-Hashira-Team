package domain.usecase

import domain.repository.ProjectRepo
import domain.model.Project

class FindProjectAssigendToTeamUseCase (
    private val projectRepo: ProjectRepo){
    fun execute(teamId: String ) : List<Project> {
        val projects = projectRepo.getAll()
        return findProjectForTeam(projects,teamId)
    }


    private fun  findProjectForTeam( projects : List<Project> ,teamId:String ) : List<Project>
            = projects.filter{it.teamId==teamId}



}




