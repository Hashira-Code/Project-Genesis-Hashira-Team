package di

import org.koin.dsl.module

val appModules = module {
    includes(
        configModule,
        dataSourceModule,
        mapperModule,
        repoModule,
        useCaseModule,
        validatorModule
    )
}