package domain.usecase

import domain.model.entity.AttendanceStatus
import domain.model.entity.Mentee
import domain.model.exception.ValidationExeption.DataNotFoundExeption
import domain.model.exception.ValidationExeption.EntityNotFoundExeption
import domain.repository.AttendanceRepo
import domain.repository.MenteeRepo

class FindMenteeWithMostAbsencesUseCase(
    private val attendanceRepo: AttendanceRepo,
    private val menteeRepo: MenteeRepo
) {
    operator fun invoke(): Result<Mentee> {
        val attendances = attendanceRepo.getAll().getOrElse {
            return Result.failure(it)
        }

        val menteeIdWithMostAbsences = attendances
            .filter { it.status == AttendanceStatus.ABSENT }
            .groupBy { it.menteeId }
            .maxByOrNull { it.value.size }
            ?.key ?: return Result.failure(DataNotFoundExeption(NO_ABSENCES_FOUND_MSG))

        val mentees = menteeRepo.getAll().getOrElse {
            return Result.failure(it)
        }

        val mentee = mentees.find { it.id == menteeIdWithMostAbsences }
            ?: return Result.failure(EntityNotFoundExeption(MENTEE_NOT_FOUND_MSG))

        return Result.success(mentee)
    }

    companion object {
        const val NO_ABSENCES_FOUND_MSG = "No absences found"
        const val MENTEE_NOT_FOUND_MSG = "Mentee not found"
    }
}