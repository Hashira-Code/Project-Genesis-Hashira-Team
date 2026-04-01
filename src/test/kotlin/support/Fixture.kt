package support

import domain.model.entity.Attendance
import domain.model.entity.AttendanceStatus
import domain.model.entity.Mentee
import domain.model.entity.Project
import domain.model.entity.Team

object Fixture {
     fun createTeam(
        id: String = "alpha",
        name: String = "Alpha Team",
        mentorLead: String = "Sarah"
    ) = Team.create(id, name, mentorLead)

     fun createProject(
        id: String = "p01",
        name: String = "Helios Initiative",
        teamId: String = "alpha"
    ) = Project.create(id, name, teamId)

    fun createMentee(
        id: String = "m001",
        name: String = "Ali",
        teamId: String = "alpha"
    ) = Mentee.create(id, name, teamId)

    fun createAttendance(
        menteeId: String = "m001",
        weekNumber: Int = 1,
        status: AttendanceStatus = AttendanceStatus.ABSENT
    ) = Attendance(menteeId, weekNumber, status)
}