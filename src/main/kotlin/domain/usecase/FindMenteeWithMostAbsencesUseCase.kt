package domain.usecase

import domain.model.entity.AttendanceStatus
import domain.model.entity.Mentee
import domain.repository.AttendanceRepo
import domain.repository.MenteeRepo

class FindMenteeWithMostAbsencesUseCase(
    private val attendanceRepo: AttendanceRepo,
    private val menteeRepo: MenteeRepo
) {
    operator fun invoke(): Result<Mentee?> {
        return attendanceRepo.getAll().mapCatching { attendances ->
            val menteeIdWithMostAbsences = attendances
                .filter { it.status == AttendanceStatus.ABSENT }
                .groupBy { it.menteeId }
                .maxByOrNull { it.value.size }
                ?.key ?: return@mapCatching null

            menteeRepo.getAll().getOrThrow()
                .find { it.id == menteeIdWithMostAbsences }
        }
    }
}