package domain.repository

import domain.model.entity.Mentee

interface MenteeRepo {
    suspend fun getAll(): Result<List<Mentee>>
    suspend fun getById(id: String): Result<Mentee?>
    suspend fun getByTeamId(teamId: String): Result<List<Mentee>>
}

