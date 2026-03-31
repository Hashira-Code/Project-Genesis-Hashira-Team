package di

import data.dataSource.CsvDataSource
import data.dataSource.DataSource
import org.koin.dsl.module
import data.validation.Validator
import java.io.File
val dataSourceModule = module {

    single<DataSource> {

        CsvDataSource(
            path = get<AppConfig>().resourcesPath,
            fileValidator = get<Validator<File>>(),
            lineValidator = get<Validator<String>>(),
            emptyFieldValidator = get<Validator<List<String>>>()


        )
    }
}