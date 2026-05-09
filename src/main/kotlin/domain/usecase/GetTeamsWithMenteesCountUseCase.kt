package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.Team
import domain.repository.MenteeRepo
import domain.repository.TeamRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetTeamsWithMenteesCountUseCase(
    private val teamRepo: TeamRepo,
    private val menteeRepo: MenteeRepo
) {

    suspend operator fun invoke(): Result<List<Pair<String, Int>>> = coroutineScope {
        val menteesDeferred = async { menteeRepo.getAll() }
        val teamsDeferred = async { teamRepo.getAll() }

        val mentees = menteesDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val teams = teamsDeferred.await().getOrElse {
            return@coroutineScope Result.failure(it)
        }
        val menteesCountPerTeam = countMenteesByTeam(mentees)
        val result = mapTeamsToCount(teams, menteesCountPerTeam)

        Result.success(result)
    }

    private fun countMenteesByTeam(mentees: List<Mentee>): Map<String, Int> {
        return mentees.groupingBy { it.teamId }.eachCount()
    }

    private fun mapTeamsToCount(
        teams: List<Team>,
        menteesCountPerTeam: Map<String, Int>
    ): List<Pair<String, Int>> {
        return teams.map { team ->
            team.name to (menteesCountPerTeam[team.id] ?: 0)
        }
    }
}
