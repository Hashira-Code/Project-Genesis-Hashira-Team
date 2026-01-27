package domain.repository

import domain.model.Team

interface TeamRepo {
    fun getAll():List<Team>

}