package domain.usecase

import domain.model.entity.Mentee

class MenteesWithPerfectAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: CalculateAttendancePercentageUseCase
) {

    operator fun invoke(): Result<List<String>> {
        val attendancePercentages = calculateAttendancePercentageUseCase().getOrElse {
            return Result.failure(it)
        }
        val perfectAttendanceNames = filterPerfectAttendanceMentees(attendancePercentages)
        return Result.success(perfectAttendanceNames)
    }

    private fun filterPerfectAttendanceMentees(attendancePercentages: Map<Mentee, Double>): List<String> {
        return attendancePercentages
            .filterValues { percentage -> percentage == PERFECT_ATTENDANCE_PERCENTAGE }
            .keys
            .map { mentee -> mentee.name }
    }

    companion object {
        private const val PERFECT_ATTENDANCE_PERCENTAGE = 100.0

    }

}