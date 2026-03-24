package di

import data.validation.EmptyFieldValidator
import data.validation.FileValidator
import data.validation.LineIsNotEmptyValidator
import data.validation.Validator
import domain.validation.WeekNumberValidator
import org.koin.dsl.module
import java.io.File
import domain.validation.Validator as DomainValidator

class ValidatorModule {
    val validatorModule = module {
        single<Validator<File>> { FileValidator() }
        single<Validator<String>> { LineIsNotEmptyValidator() }
        single<Validator<List<String>>> { EmptyFieldValidator() }
        single<DomainValidator<Int, Int>> { WeekNumberValidator() }



    }
}