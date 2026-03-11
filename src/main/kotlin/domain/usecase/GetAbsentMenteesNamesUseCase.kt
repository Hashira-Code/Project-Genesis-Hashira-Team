package domain.usecase

import domain.model.entity.AttendanceStatus
import domain.repository.MenteeRepo
import domain.repository.AttendanceRepo
import domain.model.request.WeekNumberRequest
import domain.validation.Validator

class GetAbsentMenteesNamesUseCase(
    private val attendanceRepo: AttendanceRepo,
    private val menteeRepo: MenteeRepo,
    private val weekNumberValidator: Validator<Int, Int>
) {
    operator fun invoke(request: WeekNumberRequest): Result<List<String>> {
        val weekNumber = weekNumberValidator.validate(request.weekNumber).getOrElse {
            return Result.failure(it)
        }

        val absentIds = attendanceRepo.getByWeekNumber(weekNumber).getOrElse {
            return Result.failure(it)
        }
            .asSequence()
            .filter { it.status == AttendanceStatus.ABSENT }
            .map { it.menteeId }
            .toSet()

        val names = menteeRepo.getAll().getOrElse {
            return Result.failure(it)
        }
            .asSequence()
            .filter { it.id in absentIds }
            .map { it.name }
            .toList()

        return Result.success(names)
    }
}

