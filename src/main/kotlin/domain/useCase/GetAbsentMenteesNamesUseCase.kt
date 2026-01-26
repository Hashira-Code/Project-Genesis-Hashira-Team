import domain.model.Mentee
import domain.model.Attendance
import domain.model.AttendanceStatus
import domain.repository.MenteeRepo
import domain.repository.AttendanceRepo

class GetAbsentMenteesNamesUseCase(
    private val attendanceRepo: AttendanceRepo,
    private val menteeRepo: MenteeRepo
) {

    fun execute(weekNumber: Int): List<String> {
        val attendances = attendanceRepo.getAll()
        val mentees = menteeRepo.getAll()

        return findAbsentMenteesNames(
            attendances = attendances,
            mentees = mentees,
            weekNumber = weekNumber
        )
    }

    private fun findAbsentMenteesNames(
        attendances: List<Attendance>,
        mentees: List<Mentee>,
        weekNumber: Int
    ): List<String> {

        val menteesById = mentees.associateBy { it.id }

        return attendances
            .filter {
                it.weekNumber == weekNumber &&
                        it.status == AttendanceStatus.ABSENT
            }
            .mapNotNull { attendance ->
                menteesById[attendance.menteeId]?.name
            }
    }
}