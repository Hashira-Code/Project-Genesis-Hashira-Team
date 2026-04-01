import domain.repository.AttendanceRepo
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo
import domain.repository.ProjectRepo
import domain.repository.TeamRepo
import domain.validation.Validator
import domain.validation.WeekNumberValidator
import org.koin.dsl.module
import support.Fixture.createAttendance
import support.Fixture.createMentee
import support.Fixture.createPerformanceSubmission
import support.Fixture.createProject
import support.Fixture.createTeam
import support.fake.FakePerformanceRepo
import support.fake.FakeAttendanceRepo
import support.fake.FakeProjectRepo
import support.fake.FakeTeamRepo
import support.fake.FakeMenteeRepo
val repoTestModule = module {
    single<TeamRepo> { FakeTeamRepo(listOf(createTeam())) }
    single<ProjectRepo> { FakeProjectRepo(listOf(createProject())) }
    single<AttendanceRepo> { FakeAttendanceRepo(listOf(createAttendance())) }
    single<PerformanceRepo> { FakePerformanceRepo(listOf(createPerformanceSubmission())) }
    single<MenteeRepo> { FakeMenteeRepo(listOf(createMentee())) }
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
