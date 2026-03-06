package domain.repository

import domain.model.Project

interface ProjectRepo {
    fun getAll(): Result<List<Project>>
    fun getByTeamId(teamId: String): Result<List<Project>>
}