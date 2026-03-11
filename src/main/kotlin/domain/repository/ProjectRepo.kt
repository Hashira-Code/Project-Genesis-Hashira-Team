package domain.repository

import domain.model.entity.Project

interface ProjectRepo {
    fun getAll(): Result<List<Project>>
    fun getByTeamId(teamId: String): Result<List<Project>>
}

