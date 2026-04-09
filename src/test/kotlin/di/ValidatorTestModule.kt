package di

import domain.validation.Validator
import domain.validation.WeekNumberValidator
import org.koin.core.module.Module
import org.koin.dsl.module

val validatorTestModule: Module = module {
    single<Validator<Int, Int>> { WeekNumberValidator() }
}
