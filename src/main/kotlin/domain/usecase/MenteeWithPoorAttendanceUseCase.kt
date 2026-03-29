package domain.usecase

import domain.model.entity.Mentee

class MenteeWithPoorAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: CalculateAttendancePercentageUseCase
) {

    operator fun invoke(): Result<List<String>> {
        val attendancePercentages = calculateAttendancePercentageUseCase().getOrElse {
            return Result.failure(it)
        }

        val poorAttendanceMentees = filterPoorAttendanceMentees(attendancePercentages)

        return Result.success(poorAttendanceMentees)
    }

    private fun filterPoorAttendanceMentees(
        attendancePercentages: Map<Mentee, Double>
    ): List<String> {
        return attendancePercentages
            .filterValues { percentage -> percentage < POOR_ATTENDANCE_PERCENTAGE }
            .keys
            .map { mentee -> mentee.name }
    }

    companion object {
        private const val POOR_ATTENDANCE_PERCENTAGE = 50.0

    }

}