package domain.usecase
import domain.repository.MenteeRepo
import domain.repository.TeamRepo

class FindLeadMentorForMenteeUseCase(
    private val menteeRepo: MenteeRepo,
    private val teamRepo: TeamRepo
) {

    operator fun invoke(menteeId: String): String? {

        val mentee = menteeRepo.getById(menteeId) ?: return null
        val team = teamRepo.getById(mentee.teamId) ?: return null
        return team.mentorLead
    }
}
