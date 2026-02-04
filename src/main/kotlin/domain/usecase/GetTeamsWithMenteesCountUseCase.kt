package domain.usecase
import domain.repository.MenteeRepo
import domain.repository.TeamRepo
class GetTeamsWithMenteesCountUseCase(
    private val teamRepo: TeamRepo,
    private val menteeRepo: MenteeRepo
) {

    operator fun invoke(): List<Pair<String, Int>> {
        val menteesCountPerTeam =
            menteeRepo.getAll()
                .groupingBy { it.teamId }
                .eachCount()
        return teamRepo.getAll()
            .map { team ->
                team.name to (menteesCountPerTeam[team.id] ?: 0)
            }
    }
}