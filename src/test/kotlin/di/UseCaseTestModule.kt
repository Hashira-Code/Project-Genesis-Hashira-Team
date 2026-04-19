package di

import domain.usecase.EvaluateTeamHealthUseCase
import domain.usecase.FindMenteeWithMostAbsencesUseCase
import domain.usecase.FindLeadMentorForMenteeUseCase
import domain.usecase.FindTeamWorkingOnProjectUseCase
import domain.usecase.GenerateCrossTeamPerformanceReportUseCase
import domain.usecase.GetAbsentMenteesNamesUseCase
import domain.usecase.GetAverageScorePerMenteeUseCase
import domain.usecase.GetOverallPerformanceAverageForTeamUseCase
import domain.usecase.GetTopPerformingMenteesBySubmissionTypeUseCase
import domain.usecase.CalculateOverallAttendancePercentageUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val useCaseTestModule: Module = module {
    factory { GetAbsentMenteesNamesUseCase(get(), get(), get()) }
    factory { GetAverageScorePerMenteeUseCase(get(), get()) }
    factory { EvaluateTeamHealthUseCase(get(), get(), get(), get()) }
    factory { GetOverallPerformanceAverageForTeamUseCase(get(), get()) }
    factory { FindTeamWorkingOnProjectUseCase(get(),get()) }
    factory { GetTopPerformingMenteesBySubmissionTypeUseCase(get(),get()) }
    factory { FindMenteeWithMostAbsencesUseCase(get(), get()) }
    factory { FindLeadMentorForMenteeUseCase(get(), get()) }
    factory { GenerateCrossTeamPerformanceReportUseCase(get(), get(), get(), get()) }
    factory { CalculateOverallAttendancePercentageUseCase(get())}
    }
