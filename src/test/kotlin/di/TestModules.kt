package di

import org.koin.dsl.module

val testModule = module {
    includes(
        repoTestModule,
        validatorTestModule,
        useCaseTestModule
    )
}