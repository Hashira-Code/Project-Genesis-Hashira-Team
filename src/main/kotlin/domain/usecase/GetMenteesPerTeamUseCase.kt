package domain.usecase

import domain.repository.MenteeRepo
import domain.repository.TeamRepo
import domain.model.Mentee
import domain.model.Team

class GetMenteesPerTeamUseCase(
    private val menteeRepo: MenteeRepo,
    private val teamRepo: TeamRepo
) {

    operator fun invoke(): Map<Team, List<Mentee>> {

        val teams = teamRepo.getAll()
        val mentees = menteeRepo.getAll()

        if (teams.isEmpty()) return emptyMap()

        val menteesByTeamId = mentees.groupBy { it.teamId }

        return teams.associateWith { team ->
            menteesByTeamId[team.id].orEmpty()
        }
    }
}


