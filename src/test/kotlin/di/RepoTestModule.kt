package di

import domain.model.entity.Attendance
import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.Project
import domain.model.entity.Team
import domain.repository.AttendanceRepo
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import domain.repository.ProjectRepo
import domain.repository.TeamRepo
import org.koin.core.module.Module
import org.koin.dsl.module
import data.fake.FakeAttendanceRepo
import data.fake.FakeMenteeRepo
import data.fake.FakePerformanceRepo
import data.fake.FakeProjectRepo
import data.fake.FakeTeamRepo
import data.fixture.TestDataFactory

fun repoTestModule(
    teams: List<Team> = TestDataFactory.defaultTeams(),
    projects: List<Project> = TestDataFactory.defaultProjects(),
    attendances: List<Attendance> = TestDataFactory.defaultAttendances(),
    performances: List<PerformanceSubmission> = TestDataFactory.defaultPerformanceSubmissions(),
    mentees: List<Mentee> = TestDataFactory.defaultMentees()
): Module = module {
    single<TeamRepo> { FakeTeamRepo(teams) }
    single<ProjectRepo> { FakeProjectRepo(projects) }
    single<AttendanceRepo> { FakeAttendanceRepo(attendances) }
    single<PerformanceRepo> { FakePerformanceRepo(performances) }
    single<MenteeRepo> { FakeMenteeRepo(mentees) }
}
