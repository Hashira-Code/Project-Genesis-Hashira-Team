package di

import data.repository.*
import domain.repository.*
import org.koin.dsl.module

val repoModule=module {
    single<MenteeRepo> { MenteeRepoImpl(get(),get())}
    single<TeamRepo> { TeamRepoImpl(get(),get())}
    single<ProjectRepo> { ProjectRepoImpl(get(),get())}
    single<AttendanceRepo> { AttendanceRepoImpl(get(),get())}
    single<PerformanceRepo> { PerformanceRepoImpl(get(),get())}
}
