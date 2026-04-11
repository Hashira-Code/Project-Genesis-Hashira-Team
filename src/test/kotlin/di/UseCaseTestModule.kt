package di

import domain.usecase.EvaluateTeamHealthUseCase
import domain.usecase.FindTeamWorkingOnProjectUseCase
import domain.usecase.GetAbsentMenteesNamesUseCase
import domain.usecase.GetAverageScorePerMenteeUseCase
import domain.usecase.GetOverallPerformanceAverageForTeamUseCase
import domain.usecase.GetTopPerformingMenteesBySubmissionTypeUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val useCaseTestModule: Module = module {
    single { FindTeamWorkingOnProjectUseCase(get(), get()) }
    single { GetAbsentMenteesNamesUseCase(get(), get(), get()) }
    single { GetAverageScorePerMenteeUseCase(get(), get()) }
    single { GetOverallPerformanceAverageForTeamUseCase(get(), get()) }
    single { GetTopPerformingMenteesBySubmissionTypeUseCase(get(), get()) }
    single { EvaluateTeamHealthUseCase(get(), get(), get(), get()) }
}