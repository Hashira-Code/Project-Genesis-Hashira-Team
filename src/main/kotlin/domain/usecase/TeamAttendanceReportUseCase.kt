package domain.usecase

import domain.repository.TeamRepo

class TeamAttendanceReportUseCase(
    private val teamRepo: TeamRepo,
    private val calculateAttendancePercentage: CalculateAttendancePercentageUseCase
) {
  
}
