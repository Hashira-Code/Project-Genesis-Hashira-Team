package di

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

val repoTestModule: Module = module {
    single<TeamRepo> { FakeTeamRepo(TestDataFactory.defaultTeams()) }
    single<ProjectRepo> { FakeProjectRepo(TestDataFactory.defaultProjects()) }
    single<AttendanceRepo> { FakeAttendanceRepo(TestDataFactory.defaultAttendances()) }
    single<PerformanceRepo> { FakePerformanceRepo(TestDataFactory.defaultPerformanceSubmissions()) }
    single<MenteeRepo> { FakeMenteeRepo(TestDataFactory.defaultMentees()) }
}