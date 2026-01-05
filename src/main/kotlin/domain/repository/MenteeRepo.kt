package domain.repository

import domain.model.Mentee

interface MenteeRepo {
    fun getAll(): List<Mentee>
    fun getById(id:String): Mentee?
    fun getByTeamId(teamId:String): List<Mentee>
}