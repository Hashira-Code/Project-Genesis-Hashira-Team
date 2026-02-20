package domain.usecase
import domain.repository.MenteeRepo
import domain.repository.TeamRepo
import domain.request.FindLeadMentorForMenteeRequest
import domain.validation.MenteeIdValidator

class FindLeadMentorForMenteeUseCase(
    private val menteeRepo: MenteeRepo,
    private val teamRepo: TeamRepo,
    private val menteeIdValidator: MenteeIdValidator
) {
    operator fun invoke(request: FindLeadMentorForMenteeRequest): Result<String> {
        menteeIdValidator.validate(request.menteeId)
            .onFailure { return Result.failure(it) }

        val mentee = menteeRepo.getById(request.menteeId)
            ?: return Result.failure(Exception("Mentee not found"))

        val team = teamRepo.getById(mentee.teamId)
            ?: return Result.failure(Exception("Team not found"))

        return Result.success(team.mentorLead)
    }
}
