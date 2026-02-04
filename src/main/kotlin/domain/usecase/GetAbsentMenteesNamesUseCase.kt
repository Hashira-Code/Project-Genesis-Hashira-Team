import domain.model.Mentee
import domain.model.Attendance
import domain.model.AttendanceStatus
import domain.repository.MenteeRepo
import domain.repository.AttendanceRepo

class GetAbsentMenteesNamesUseCase(
    private val attendanceRepo: AttendanceRepo,
    private val menteeRepo: MenteeRepo
) {

    operator fun invoke(weekNumber: Int): List<String> {

        require(weekNumber > 0)

        val absentIds = attendanceRepo
            .getByWeekNumber(weekNumber)
            .asSequence()
            .filter { it.status == AttendanceStatus.ABSENT }
            .map { it.menteeId }
            .toSet()

        if (absentIds.isEmpty()) return emptyList()

        return menteeRepo.getAll()
            .asSequence()
            .filter { it.id in absentIds }
            .map { it.name }
            .toList()
    }

}