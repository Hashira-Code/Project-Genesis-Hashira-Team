package domain.usecase

import domain.model.Team
import domain.model.Mentee
import domain.repository.MenteeRepo
import domain.repository.TeamRepo

class GetMenteesNamesPerTeamUseCase(
    private val menteesRepo: MenteeRepo,
    private val teamRepo: TeamRepo
) {
    fun execute(): Map<String,List<String>> {
        val mentees = menteesRepo.getAll()
        val teams= teamRepo.getAll()
        return mapMenteesNamesPerTeam(mentees ,teams)



    }


    private fun mapMenteesNamesPerTeam(
        mentees: List<Mentee>,
         teams: List<Team>): Map<String, List<String>> {
        val menteesByTeamId = mentees.groupBy { mentee ->
            mentee.teamId
        }
        return teams.associate { team ->
            team.name to menteesByTeamId[team.id].orEmpty().map { it.name }


        }


    }
}