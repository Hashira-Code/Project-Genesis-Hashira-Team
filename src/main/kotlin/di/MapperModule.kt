package di

import data.mapper.Mapper
import data.mapper.PerformanceMapper
import data.mapper.TeamMapper
import data.mapper.MenteeMapper
import data.mapper.ProjectMapper
import data.mapper.AttendanceMapper
import data.model.TeamRaw
import data.model.ProjectRaw
import data.model.MenteeRaw
import data.model.AttendanceRaw
import data.model.PerformanceRaw
import domain.model.entity.Team
import domain.model.entity.Project
import domain.model.entity.Mentee
import domain.model.entity.Attendance
import domain.model.entity.PerformanceSubmission
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mapperModule = module {
    single<Mapper<AttendanceRaw, Attendance>>(named("attendanceMapper")) { AttendanceMapper() }
    single<Mapper<ProjectRaw, Project>>(named("projectMapper")) { ProjectMapper() }
    single<Mapper<TeamRaw, Team>>(named("teamMapper")) { TeamMapper() }
    single<Mapper<MenteeRaw, Mentee>>(named("menteeMapper")) { MenteeMapper() }
    single<Mapper<PerformanceRaw, PerformanceSubmission>>(named("performanceMapper")) { PerformanceMapper() }
}
