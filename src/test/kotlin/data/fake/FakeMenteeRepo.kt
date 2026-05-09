package data.fake

import domain.model.entity.Mentee
import domain.repository.MenteeRepo

class FakeMenteeRepo(private val mentees: List<Mentee> = emptyList()) : MenteeRepo {
    override suspend fun getAll(): Result<List<Mentee>> = Result.success(mentees)

    override suspend fun getById(id: String): Result<Mentee?> =
        Result.success(mentees.find { it.id == id })

    override suspend fun getByTeamId(teamId: String): Result<List<Mentee>> =
        Result.success(mentees.filter { it.teamId == teamId })
}