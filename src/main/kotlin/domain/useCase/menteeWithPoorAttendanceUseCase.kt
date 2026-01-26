package domain.useCase

class menteeWithPoorAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: calculateAttendancePercentageUseCase) {

    fun execute(): List<String>{
        return calculateAttendancePercentageUseCase.execute().filter { percentage ->
            percentage.second < 50 }.map{
            it.first.name }
    }
}