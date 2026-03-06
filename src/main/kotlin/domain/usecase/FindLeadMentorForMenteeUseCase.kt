package domain.usecase

import domain.repository.MenteeRepo
import domain.repository.TeamRepo
import domain.model.request.MenteeIdRequest
import domain.model.exception.ValidationException.EntityNotFoundException
import domain.validation.Validator

class FindLeadMentorForMenteeUseCase(
    private val menteeRepo: MenteeRepo,
    private val teamRepo: TeamRepo,
    private val menteeIdValidator: Validator<String, String>
) {
    operator fun invoke(request: MenteeIdRequest): Result<String> {
        menteeIdValidator.validate(request.id)
            .onFailure { return Result.failure(it) }

        val mentee = menteeRepo.getById(request.id).getOrElse {
            return Result.failure(it)
        } ?: return Result.failure(EntityNotFoundException(MENTEE_NOT_FOUND_MSG))

        val team = teamRepo.getById(mentee.teamId).getOrElse {
            return Result.failure(it)
        } ?: return Result.failure(EntityNotFoundException(TEAM_NOT_FOUND_MSG))


        return Result.success(team.mentorLead)
    }

    companion object {
        const val MENTEE_NOT_FOUND_MSG = "Mentee not found"
        const val TEAM_NOT_FOUND_MSG = "Team not found"
    }

}
