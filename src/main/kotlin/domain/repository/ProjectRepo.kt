package domain.repository

import domain.model.entity.Project

interface ProjectRepo {
    suspend fun getAll(): Result<List<Project>>
    suspend fun getByTeamId(teamId: String): Result<List<Project>>
}

