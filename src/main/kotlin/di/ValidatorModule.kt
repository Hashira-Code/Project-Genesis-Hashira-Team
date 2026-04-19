package di

import data.validation.EmptyFieldValidator
import data.validation.FileValidator
import data.validation.LineIsNotEmptyValidator
import data.validation.Validator
import domain.validation.ScoreValidator
import domain.validation.WeekNumberValidator
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File
import domain.validation.Validator as DomainValidator

val validatorModule = module {
    single<Validator<File>>(named("fileValidator")) { FileValidator() }
    single<Validator<String>>(named("lineValidator")) { LineIsNotEmptyValidator() }
    single<Validator<List<String>>>(named("emptyFieldValidator")) { EmptyFieldValidator() }
    single<DomainValidator<Int, Int>> { WeekNumberValidator() }
    single<DomainValidator<Double, Double>> { ScoreValidator() }
}
