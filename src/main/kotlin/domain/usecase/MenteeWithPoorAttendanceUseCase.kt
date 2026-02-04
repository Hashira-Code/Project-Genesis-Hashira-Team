package domain.usecase

class MenteeWithPoorAttendanceUseCase(
    private val calculateAttendancePercentageUseCase: CalculateAttendancePercentageUseCase) {

    operator fun invoke(): List<String>{
        return calculateAttendancePercentageUseCase().filterValues { percentage ->
            percentage<50.0 }.keys.map{mentee ->
            mentee.name}
    }

}