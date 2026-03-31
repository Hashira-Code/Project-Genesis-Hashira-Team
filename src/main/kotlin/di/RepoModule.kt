package di

import data.repository.*
import domain.repository.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repoModule=module {
    single<MenteeRepo> { MenteeRepoImpl(get(), get(named("menteeMapper"))) }
    single<TeamRepo> { TeamRepoImpl(get(), get(named("teamMapper"))) }
    single<ProjectRepo> { ProjectRepoImpl(get(), get(named("projectMapper"))) }
    single<AttendanceRepo> { AttendanceRepoImpl(get(), get(named("attendanceMapper"))) }
    single<PerformanceRepo> { PerformanceRepoImpl(get(), get(named("performanceMapper"))) }
}
