package domain.repository

import domain.model.Team

interface TeamRepo {
    fun getAll():List<Team>
    fun getById(id:String):Team?
}