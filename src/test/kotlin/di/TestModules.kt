package di

import org.koin.dsl.module

val testModule = module {
        repoTestModule
        validatorTestModule
        useCaseTestModule
}

