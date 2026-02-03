package domain.usecase

class MenteesWithPerfectAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: CalculateAttendancePercentageUseCase) {

    fun execute(): List<String>{
        return calculateAttendancePercentageUseCase.execute().filter { percentage ->
            percentage.second==100.0 }.map{
            it.first.name
        }
    }
}