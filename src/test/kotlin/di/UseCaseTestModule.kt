package di

import domain.usecase.GetAbsentMenteesNamesUseCase
import domain.usecase.GetAverageScorePerMenteeUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

fun useCaseTestModule(): Module = module {
    single { GetAbsentMenteesNamesUseCase(get(), get(), get()) }
    single { GetAverageScorePerMenteeUseCase(get(), get()) }


}
