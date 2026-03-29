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

        val absentIds = getAbsentMenteeIds(weekNumber).getOrElse {
            return Result.failure(it)
        }

        return getMenteeNamesByIds(absentIds)
    }

    private fun getAbsentMenteeIds(weekNumber: Int): Result<Set<Any>> {
        return attendanceRepo.getByWeekNumber(weekNumber).map { attendanceList ->
            attendanceList.asSequence()
                .filter { it.status == AttendanceStatus.ABSENT }
                .map { it.menteeId }
                .toSet()
        }
    }

    private fun getMenteeNamesByIds(absentIds: Set<Any>): Result<List<String>> {
        return menteeRepo.getAll().map { mentees ->
            mentees.asSequence()
                .filter { it.id in absentIds }
                .map { it.name }
                .toList()
        }
    }
}

