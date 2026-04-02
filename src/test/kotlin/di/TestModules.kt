package di

import domain.model.entity.Attendance
import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.Project
import domain.model.entity.Team
import org.koin.core.module.Module
import support.Fixture

fun defaultTestModules(
    teams: List<Team> = Fixture.teams(),
    projects: List<Project> = Fixture.projects(),
    attendances: List<Attendance> = Fixture.attendance(),
    performances: List<PerformanceSubmission> = Fixture.performances(),
    mentees: List<Mentee> = Fixture.mentees()
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
