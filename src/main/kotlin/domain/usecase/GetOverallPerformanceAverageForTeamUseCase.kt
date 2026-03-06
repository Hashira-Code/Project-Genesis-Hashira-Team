package domain.usecase

import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import domain.model.request.TeamIdRequest
import domain.validation.Validator
import domain.model.exception.ValidationException.DataNotFoundException

class GetOverallPerformanceAverageForTeamUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo,
    private val teamIdValidator: Validator<String, String>
) {

    operator fun invoke(request: TeamIdRequest): Result<Double> {
        return teamIdValidator.validate(request.id).fold(
            onSuccess = { teamId ->
                val menteeIds = menteeRepo
                    .getByTeamId(teamId).getOrThrow()
                    .asSequence()
                    .map { it.id }
                    .toSet()

                if (menteeIds.isEmpty()) {
                    Result.failure(DataNotFoundException(NO_DATA_MSG))
                } else {
                    val average = performanceRepo
                        .getAll().getOrThrow()
                        .asSequence()
                        .filter { it.menteeId in menteeIds }
                        .map { it.score }
                        .toList()
                        .averageOrZero()

                    Result.success(average)
                }
            },
            onFailure = { error -> Result.failure(error) }
        )
    }

    private fun List<Double>.averageOrZero(): Double {
        return if (this.isEmpty()) 0.0 else this.average()
    }

    companion object {
        private const val NO_DATA_MSG =
            "No mentees found for this team"
    }
}
