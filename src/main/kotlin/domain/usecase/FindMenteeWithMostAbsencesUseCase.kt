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

        return TODO("Provide the return value")
    }
}