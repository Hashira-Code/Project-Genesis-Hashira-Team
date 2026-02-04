package domain.repository

import domain.model.Project

interface ProjectRepo {
    fun getAll():List<Project>
    fun getByTeamId(teamId:String):List<Project>
}