package di

import domain.usecase.EvaluateTeamHealthUseCase
import domain.usecase.FindTeamWorkingOnProjectUseCase
import domain.usecase.GetAbsentMenteesNamesUseCase
import domain.usecase.GetAverageScorePerMenteeUseCase
import domain.usecase.GetOverallPerformanceAverageForTeamUseCase
import org.koin.core.module.Module
import org.koin.core.scope.get
import org.koin.dsl.module

val useCaseTestModule: Module = module {
    factory { GetAbsentMenteesNamesUseCase(get(), get(), get()) }
    factory { GetAverageScorePerMenteeUseCase(get(), get()) }
    factory { EvaluateTeamHealthUseCase(get(), get(), get(), get()) }
    factory { GetAbsentMenteesNamesUseCase(get(), get(),get()) }
    factory { GetOverallPerformanceAverageForTeamUseCase(get(), get()) }
    factory { FindTeamWorkingOnProjectUseCase(get(),get()) }
}