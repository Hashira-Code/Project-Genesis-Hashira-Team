package domain.repository

import domain.model.entity.Team

interface TeamRepo {
    suspend fun getAll(): Result<List<Team>>
    suspend fun getById(id: String): Result<Team?>
}

