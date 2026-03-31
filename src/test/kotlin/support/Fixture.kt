package support

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
}