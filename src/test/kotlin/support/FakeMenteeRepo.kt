package support

import domain.model.entity.Mentee
import domain.repository.MenteeRepo

class FakeMenteeRepo(private val Mentees: List<Mentee> = emptyList()) : MenteeRepo {
    override fun getAll(): Result<List<Mentee>> = Result.success(Mentees)

    override fun getById(id: String): Result<Mentee?> =
        Result.success(Mentees.find { it.id == id })

    override fun getByTeamId(teamId: String): Result<List<Mentee>> =
        Result.success(Mentees.filter { it.teamId == teamId })
}