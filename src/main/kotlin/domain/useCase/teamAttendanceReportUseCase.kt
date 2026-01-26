package domain.useCase

import domain.repository.TeamRepo

class teamAttendanceReportUseCase(
    private val teamRepo: TeamRepo,
    private val calculateAttendancePercentageUseCase: calculateAttendancePercentageUseCase
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