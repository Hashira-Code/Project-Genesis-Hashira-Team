package domain.repository

import domain.model.entity.Mentee

interface MenteeRepo {
    fun getAll(): Result<List<Mentee>>
    fun getById(id: String): Result<Mentee?>
    fun getByTeamId(teamId: String): Result<List<Mentee>>
}

