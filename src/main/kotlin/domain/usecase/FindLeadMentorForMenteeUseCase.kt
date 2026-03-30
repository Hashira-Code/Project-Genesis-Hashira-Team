package domain.usecase

import domain.model.entity.Mentee
import domain.model.entity.Team
import domain.repository.MenteeRepo
import domain.repository.TeamRepo
import domain.model.request.MenteeIdRequest
import domain.model.exception.ValidationException.EntityNotFoundException
import domain.validation.Validator

class FindLeadMentorForMenteeUseCase(
    private val menteeRepo: MenteeRepo,
    private val teamRepo: TeamRepo,
) {
    operator fun invoke(request: MenteeIdRequest): Result<String> {
        val mentee = menteeRepo.getById(request.id).getOrElse {
            return Result.failure(it)
        } ?: return Result.failure(EntityNotFoundException(MENTEE_NOT_FOUND_MSG))

        val team = fetchTeamForMentee(mentee).getOrElse {
            return Result.failure(it)
        }
        return Result.success(team.mentorLead)
    }

    private fun fetchTeamForMentee(mentee: Mentee): Result<Team> {
        val team = teamRepo.getById(mentee.teamId).getOrElse {
            return Result.failure(it)
        } ?: return Result.failure(EntityNotFoundException(TEAM_NOT_FOUND_MSG))

        return Result.success(team)
    }

    companion object {
        const val MENTEE_NOT_FOUND_MSG = "Mentee not found"
        const val TEAM_NOT_FOUND_MSG = "Team not found"
    }
}