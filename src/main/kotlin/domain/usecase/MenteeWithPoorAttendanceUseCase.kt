package domain.usecase

class MenteeWithPoorAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: CalculateAttendancePercentageUseCase
) {

    operator fun invoke(): Result<List<String>> {
        val attendancePercentages = calculateAttendancePercentageUseCase().getOrElse {
            return Result.failure(it)
        }

        val poorAttendanceMentees = attendancePercentages
            .filterValues { percentage -> percentage < POOR_ATTENDANCE_PERCENTAGE }
            .keys
            .map { mentee -> mentee.name }

        return Result.success(poorAttendanceMentees)
    }

    companion object {
        private const val POOR_ATTENDANCE_PERCENTAGE = 50.0

    }

}