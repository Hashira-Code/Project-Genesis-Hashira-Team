package di

import org.koin.dsl.module

val testModule = module {
    includes(
        repositoryTestModule,
        validatorTestModule,
        useCaseTestModule
    )
}