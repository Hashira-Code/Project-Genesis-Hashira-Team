package domain.usecase

import domain.model.entity.PerformanceSubmission
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
        val menteeIds = getMenteeIdsByTeam(request.id).getOrElse {
            return Result.failure(it)
        }
        if (menteeIds.isEmpty()) {
            return Result.failure(DataNotFoundException(NO_DATA_MSG))
        }
        val allSubmissions = performanceRepo.getAll().getOrElse {
            return Result.failure(it)
        }
        val average = calculateTeamAverage(allSubmissions, menteeIds)

        return Result.success(average)

    }

    private fun getMenteeIdsByTeam(teamId: String): Result<Set<String>> {
        return menteeRepo.getByTeamId(teamId).map { mentees ->
            mentees.map { it.id }.toSet()
        }
    }

    private fun calculateTeamAverage(
        submissions: List<PerformanceSubmission>,
        menteeIds: Set<String>
    ): Double {
        return submissions
            .asSequence()
            .filter { it.menteeId in menteeIds }
            .map { it.score }
            .toList()
            .averageOrZero()
    }

    private fun List<Double>.averageOrZero(): Double {
        return if (this.isEmpty()) 0.0 else this.average()
    }

    companion object {
        private const val NO_DATA_MSG =
            "No mentees found for this team"
    }
}