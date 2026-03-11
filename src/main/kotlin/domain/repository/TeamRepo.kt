package domain.repository

import domain.model.entity.Team

interface TeamRepo {
    fun getAll(): Result<List<Team>>
    fun getById(id: String): Result<Team?>
}

