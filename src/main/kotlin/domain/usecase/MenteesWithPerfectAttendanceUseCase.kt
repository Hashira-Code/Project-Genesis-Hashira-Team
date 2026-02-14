package domain.usecase

class MenteesWithPerfectAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: CalculateAttendancePercentageUseCase) {

    operator fun invoke(): List<String>{
        return calculateAttendancePercentageUseCase().filterValues { percentage ->
            percentage==PERFECT_ATTENDANCE_PERCENTAGE }.keys.map{mentee ->
                mentee.name}
        }
    companion object {
        private const val PERFECT_ATTENDANCE_PERCENTAGE=100.0

    }

}