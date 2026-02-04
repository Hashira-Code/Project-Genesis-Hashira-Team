package domain.usecase

class MenteesWithPerfectAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: CalculateAttendancePercentageUseCase) {

    operator fun invoke(): List<String>{
        return calculateAttendancePercentageUseCase().filterValues { percentage ->
            percentage==100.0 }.keys.map{mentee ->
                mentee.name}
        }

}