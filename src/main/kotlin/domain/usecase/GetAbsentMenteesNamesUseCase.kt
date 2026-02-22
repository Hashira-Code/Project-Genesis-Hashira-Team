import domain.model.Mentee
import domain.model.Attendance
import domain.model.AttendanceStatus
import domain.repository.MenteeRepo
import domain.repository.AttendanceRepo
import domain.request.WeekNumberRequest
import domain.validation.Validator

class GetAbsentMenteesNamesUseCase(
    private val attendanceRepo: AttendanceRepo,
    private val menteeRepo: MenteeRepo,
    private val weekNumberValidator: Validator<String, Int>
) {

    operator fun invoke(request: WeekNumberRequest): Result<List<String>> {
        return weekNumberValidator.validate(request.rawWeekNumber)
            .fold(
            onSuccess = { weekNumber ->
                val absentIds = attendanceRepo
                    .getByWeekNumber(weekNumber)
                    .asSequence()
                    .filter { it.status == AttendanceStatus.ABSENT }
                    .map { it.menteeId }
                    .toSet()
                val names = menteeRepo
                    .getAll()
                    .asSequence()
                    .filter { it.id in absentIds }
                    .map { it.name }
                    .toList()
                Result.success(names)

            }
            ,onFailure = { error -> Result.failure(error) }


        )
    }


}