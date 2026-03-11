package domain.usecase

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
        val menteesCountPerTeam =
            mentees.groupingBy { it.teamId }.eachCount()
        val teams = teamRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val result = teams.map { team ->
            team.name to (menteesCountPerTeam[team.id] ?: 0)
        }
        return Result.success(result)
    }

}