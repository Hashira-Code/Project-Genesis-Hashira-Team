import domain.repository.AttendanceRepo
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import domain.repository.ProjectRepo
import domain.repository.TeamRepo
import domain.validation.Validator
import domain.validation.WeekNumberValidator
import org.koin.dsl.module
import support.fake.FakeAttendanceRepo
import support.fake.FakeMenteeRepo
import support.fake.FakePerformanceRepo
import support.fake.FakeProjectRepo
import support.fake.FakeTeamRepo
import support.Fixture.attendanceList
import support.Fixture.menteeList
import support.Fixture.performanceSubmissionList
import support.Fixture.projectList
import support.Fixture.teamList
val repoTestModule = module {
    single<TeamRepo> { FakeTeamRepo(teamList()) }
    single<ProjectRepo> { FakeProjectRepo(projectList()) }
    single<AttendanceRepo> { FakeAttendanceRepo(attendanceList()) }
    single<PerformanceRepo> { FakePerformanceRepo(performanceSubmissionList()) }
    single<MenteeRepo> { FakeMenteeRepo(menteeList()) }
}

val validatorTestModule = module {
    single<Validator<Int, Int>> { WeekNumberValidator() }
}

val testModules = module {
    includes(
        repoTestModule,
        validatorTestModule
    )
}
