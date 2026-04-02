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
import support.Fixture
import support.fake.FakeAttendanceRepo
import support.fake.FakeMenteeRepo
import support.fake.FakePerformanceRepo
import support.fake.FakeProjectRepo
import support.fake.FakeTeamRepo

fun repoTestModule(
    teams: List<Team> = Fixture.teams(),
    projects: List<Project> = Fixture.projects(),
    attendances: List<Attendance> = Fixture.attendance(),
    performances: List<PerformanceSubmission> = Fixture.performances(),
    mentees: List<Mentee> = Fixture.mentees()
): Module = module {
    single<TeamRepo> { FakeTeamRepo(teams) }
    single<ProjectRepo> { FakeProjectRepo(projects) }
    single<AttendanceRepo> { FakeAttendanceRepo(attendances) }
    single<PerformanceRepo> { FakePerformanceRepo(performances) }
    single<MenteeRepo> { FakeMenteeRepo(mentees) }
}
