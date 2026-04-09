package data.fixture

import domain.model.entity.Project
import domain.model.entity.Team

object FindTeamWorkingOnProjectFixture {
    data class Case(
        val name: String,
        val projects: List<Project>,
        val teams: List<Team>,
        val requestProjectId: String,
        val expectedTeamId: String? = null
    )

    val projectAndAssignedTeamExist = Case(
        name = "project and assigned team both exist",
        projects = TestDataFactory.defaultProjects(),
        teams = TestDataFactory.defaultTeams(),
        requestProjectId = "p01",
        expectedTeamId = "alpha"
    )

    val projectDoesNotExist = Case(
        name = "project does not exist",
        projects = emptyList(),
        teams = emptyList(),
        requestProjectId = "p01"
    )

    val projectExistsButAssignedTeamMissing = Case(
        name = "project exists but assigned team does not",
        projects = TestDataFactory.defaultProjects(),
        teams = emptyList(),
        requestProjectId = "p01"
    )

    val cases = listOf(
        projectAndAssignedTeamExist,
        projectDoesNotExist,
        projectExistsButAssignedTeamMissing
    )
}
