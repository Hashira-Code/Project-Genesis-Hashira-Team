package domain.usecase

class MenteesWithPerfectAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: CalculateAttendancePercentageUseCase) {

    operator fun invoke(): Result<List<String>>{
        val attendancePercentages = calculateAttendancePercentageUseCase().getOrElse {
            return Result.failure(it)
        }
        val perfectAttendanceMentees = attendancePercentages
            .filterValues { percentage -> percentage == PERFECT_ATTENDANCE_PERCENTAGE }
            .keys
            .map { mentee -> mentee.name }

        return Result.success(perfectAttendanceMentees)
    }
    companion object {
        private const val PERFECT_ATTENDANCE_PERCENTAGE=100.0

    }

}