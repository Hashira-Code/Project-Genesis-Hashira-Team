package domain.usecase

class MenteeWithPoorAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: CalculateAttendancePercentageUseCase) {

    fun execute(): List<String>{
        return calculateAttendancePercentageUseCase.execute().filter { percentage ->
            percentage.second < 50 }.map{
            it.first.name }
    }
}