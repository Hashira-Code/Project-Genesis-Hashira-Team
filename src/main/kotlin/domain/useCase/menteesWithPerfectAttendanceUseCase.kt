package domain.useCase

class menteesWithPerfectAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: calculateAttendancePercentageUseCase) {

    fun execute(): List<String>{
        return calculateAttendancePercentageUseCase.execute().filter { percentage ->
            percentage.second==100.0 }.map{
            it.first.name
        }
    }
}