package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.Team
import domain.repository.MenteeRepo
import domain.repository.TeamRepo

class GetTeamsWithMenteesCountUseCase(
    private val teamRepo: TeamRepo,
    private val menteeRepo: MenteeRepo
) {

    operator fun invoke(): Result<List<Pair<String, Int>>> {
        val mentees = menteeRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val teams = teamRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val menteesCountPerTeam = countMenteesByTeam(mentees)
        val result = mapTeamsToCount(teams, menteesCountPerTeam)

        return Result.success(result)
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