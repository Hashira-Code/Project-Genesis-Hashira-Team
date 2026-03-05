package domain.usecase
import domain.model.TeamMenteeCount
import domain.repository.MenteeRepo
import domain.repository.TeamRepo

class GetTeamsWithMenteesCountUseCase(
    private val teamRepo: TeamRepo,
    private val menteeRepo: MenteeRepo
) {

    operator fun invoke(): List<TeamMenteeCount> {
        val allMentees = menteeRepo.getAll()
        val allTeams = teamRepo.getAll()

        val menteesCountPerTeam =
            allMentees.asSequence()
                .groupingBy { it.teamId }
                .eachCount()

        return allTeams.asSequence()
            .map { team ->
                TeamMenteeCount(
                    teamName = team.name,
                    menteeCount = menteesCountPerTeam[team.id] ?: 0
                )
            }.toList()
    }
}