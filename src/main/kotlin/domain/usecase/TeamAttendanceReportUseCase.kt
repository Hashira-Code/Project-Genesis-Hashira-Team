package domain.usecase

import domain.repository.TeamRepo

class TeamAttendanceReportUseCase(
    private val teamRepo: TeamRepo,
    private val calculateAttendancePercentageUseCase: CalculateAttendancePercentageUseCase
) {
    fun execute() {
           teamRepo.getAll().forEach {team ->
            print("team Name>>>   ${team.name}")
            val teamMembers=calculateAttendancePercentageUseCase.execute().filter { mentee ->
                mentee.first.teamId==team.id }
               
            print("Attendance rate for each member >>")
            teamMembers.forEach { member ->
                print("${member.first.name}    ${member.second}")
            }

        }
    }
}
