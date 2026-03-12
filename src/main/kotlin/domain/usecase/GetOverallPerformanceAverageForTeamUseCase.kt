package domain.usecase

import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import domain.model.request.TeamIdRequest
import domain.validation.Validator
import domain.model.exception.ValidationException.DataNotFoundException

class GetOverallPerformanceAverageForTeamUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo,
) {

    operator fun invoke(request: TeamIdRequest): Result<Double> {
        val menteeIds = menteeRepo
            .getByTeamId(request.id).getOrElse {
                return Result.failure(it)
            }.map { it.id }.toSet()

        if (menteeIds.isEmpty()) {
            return Result.failure(DataNotFoundException(NO_DATA_MSG))
        }
        val allSubmissions = performanceRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val average = allSubmissions
            .asSequence()
            .filter { it.menteeId in menteeIds }
            .map { it.score }
            .toList()
            .averageOrZero()

        return Result.success(average)

    }

    private fun List<Double>.averageOrZero(): Double {
        return if (this.isEmpty()) 0.0 else this.average()
    }

    companion object {
        private const val NO_DATA_MSG =
            "No mentees found for this team"
    }
}
