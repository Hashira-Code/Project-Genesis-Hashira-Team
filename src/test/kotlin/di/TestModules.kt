package di

import domain.model.entity.Attendance
import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.Project
import domain.model.entity.Team
import org.koin.core.module.Module
import data.fixture.TestDataFactory

fun defaultTestModules(
    teams: List<Team> = TestDataFactory.defaultTeams(),
    projects: List<Project> = TestDataFactory.defaultProjects(),
    attendances: List<Attendance> = TestDataFactory.defaultAttendances(),
    performances: List<PerformanceSubmission> = TestDataFactory.defaultPerformanceSubmissions(),
    mentees: List<Mentee> = TestDataFactory.defaultMentees()
): Array<Module> = arrayOf(
    repoTestModule(
        teams = teams,
        projects = projects,
        attendances = attendances,
        performances = performances,
        mentees = mentees
    ),
    validatorTestModule(),
    useCaseTestModule()
)
