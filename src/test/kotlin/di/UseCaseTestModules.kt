package di

import domain.usecase.EvaluateTeamHealthUseCase
import domain.usecase.FindTeamWorkingOnProjectUseCase
import domain.usecase.GetAbsentMenteesNamesUseCase
import domain.usecase.GetTopPerformingMenteesBySubmissionTypeUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

fun useCaseTestModule(): Module = module {
    single { EvaluateTeamHealthUseCase(get(), get(), get(), get()) }
    single { FindTeamWorkingOnProjectUseCase(get(), get()) }
    single { GetAbsentMenteesNamesUseCase(get(), get(), get()) }
    single { GetTopPerformingMenteesBySubmissionTypeUseCase(get(), get()) }
}
