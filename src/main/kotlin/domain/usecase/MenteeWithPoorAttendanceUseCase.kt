package domain.usecase

class MenteeWithPoorAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: CalculateAttendancePercentageUseCase) {

    operator fun invoke(): List<String>{
        return calculateAttendancePercentageUseCase().filterValues { percentage ->
            percentage<POOR_ATTENDANCE_PERCENTAGE }.keys.map{mentee ->
            mentee.name}
    }
    companion object {
        private const val POOR_ATTENDANCE_PERCENTAGE=50.0

    }

}