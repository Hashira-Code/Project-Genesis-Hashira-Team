package domain.usecase

import domain.repository.MenteeRepo
import domain.repository.TeamRepo
import domain.model.entity.Mentee
import domain.model.entity.Team

class GetMenteesPerTeamUseCase(
    private val menteeRepo: MenteeRepo,
    private val teamRepo: TeamRepo
) {

    operator fun invoke(): Result<Map<Team, List<Mentee>>> {

        val teams = teamRepo.getAll().getOrElse({return Result.failure(it)})
        val mentees = menteeRepo.getAll().getOrElse { return Result.failure(it) }

        if (teams.isEmpty()) return Result.success(emptyMap())

        val menteesByTeamId = mentees.groupBy { it.teamId }

        val result = teams.associateWith { team ->
            menteesByTeamId[team.id].orEmpty()
        }
        return Result.success(result)
    }
}




