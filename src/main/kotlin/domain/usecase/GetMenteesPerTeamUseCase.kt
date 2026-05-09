package domain.usecase

import domain.repository.MenteeRepo
import domain.repository.TeamRepo
import domain.model.entity.Mentee
import domain.model.entity.Team
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetMenteesPerTeamUseCase(
    private val menteeRepo: MenteeRepo,
    private val teamRepo: TeamRepo
) {

    suspend operator fun invoke(): Result<Map<Team, List<Mentee>>> = coroutineScope {
        val teamsDeferred = async { teamRepo.getAll() }
        val menteesDeferred = async { menteeRepo.getAll() }

        val teams = teamsDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val mentees = menteesDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }

        if (teams.isEmpty()) return@coroutineScope Result.success(emptyMap())
        val result = mapMenteesToTeams(teams, mentees)
        Result.success(result)
    }

    private fun mapMenteesToTeams(
        teams: List<Team>,
        mentees: List<Mentee>
    ): Map<Team, List<Mentee>> {
        val menteesByTeamId = mentees.groupBy { it.teamId }

        return teams.associateWith { team ->
            menteesByTeamId[team.id].orEmpty()
        }
    }
}




