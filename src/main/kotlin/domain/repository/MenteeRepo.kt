package domain.repository

import domain.model.Mentee

interface MenteeRepo {
    fun getAll(): List<Mentee>

}