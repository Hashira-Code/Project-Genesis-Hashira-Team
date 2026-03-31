package di
import org.koin.dsl.module
import domain.usecase.CalculateAttendancePercentageUseCase
import domain.usecase.CalculatingMenteeAttendanceTimesUseCase
import domain.usecase.EvaluateTeamHealthUseCase
import domain.usecase.FindLeadMentorForMenteeUseCase
import domain.usecase.FindProjectsAssignedToTeamUseCase
import domain.usecase.FindTeamsWithNoProjectUseCase
import domain.usecase.FindTeamWorkingOnProjectUseCase
import domain.usecase.GetOverallPerformanceAverageForTeamUseCase
import domain.usecase.GetTopPerformingMenteesBySubmissionTypeUseCase
import domain.usecase.MenteesWithPerfectAttendanceUseCase
import domain.usecase.TeamAttendanceReportUseCase
import domain.usecase.MenteeWithPoorAttendanceUseCase
import domain.usecase.FindTopScoringMenteeOverallUseCase
import domain.usecase.FindMenteesNamesWithTasksUseCase
import domain.usecase.GetAbsentMenteesNamesUseCase
import domain.usecase.GetAverageScorePerMenteeUseCase
import domain.usecase.GetMenteesOrderedByTaskCountUseCase
import domain.usecase.GetMenteesPerTeamUseCase
import domain.usecase.GetMenteesWithMultipleSubmissionsUseCase
import domain.usecase.GetMenteesWithoutAnySubmissionUseCase
import domain.usecase.GetPerformanceBreakdownForMenteeUseCase
import domain.usecase.GetTeamsWithMenteesCountUseCase

val useCaseModule = module {
    single { CalculateAttendancePercentageUseCase(get(),get(),get()) }
    single { CalculatingMenteeAttendanceTimesUseCase(get(), get()) }
    single { EvaluateTeamHealthUseCase(get(),get(),get(),get()) }
    single { FindLeadMentorForMenteeUseCase(get(), get()) }
    single { FindMenteesNamesWithTasksUseCase(get(),get()) }
    single { FindProjectsAssignedToTeamUseCase(get()) }
    single { FindTeamsWithNoProjectUseCase(get(), get()) }
    single { FindTeamWorkingOnProjectUseCase(get(), get()) }
    single { FindTopScoringMenteeOverallUseCase(get(),get()) }
    single { GetAbsentMenteesNamesUseCase(get(), get(), get()) }
    single { GetAverageScorePerMenteeUseCase(get(), get()) }
    single { GetMenteesOrderedByTaskCountUseCase(get(), get()) }
    single { GetMenteesPerTeamUseCase(get(), get()) }
    single { GetMenteesWithMultipleSubmissionsUseCase(get(), get()) }
    single { GetMenteesWithoutAnySubmissionUseCase(get(),get()) }
    single { GetOverallPerformanceAverageForTeamUseCase(get(),get()) }
    single { GetPerformanceBreakdownForMenteeUseCase(get()) }
    single { GetTeamsWithMenteesCountUseCase(get(),get()) }
    single { GetTopPerformingMenteesBySubmissionTypeUseCase(get(),get()) }
    single { MenteesWithPerfectAttendanceUseCase(get()) }
    single { MenteeWithPoorAttendanceUseCase(get()) }
    single { TeamAttendanceReportUseCase(get(),get(),get()) }
}