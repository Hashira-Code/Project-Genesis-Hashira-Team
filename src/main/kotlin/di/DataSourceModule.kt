package di

import data.dataSource.CsvDataSource
import data.dataSource.DataSource
import data.validation.Validator
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val dataSourceModule = module {

    single<DataSource> {
        CsvDataSource(
            path = get<AppConfig>().resourcesPath,
            fileValidator = get(named("fileValidator")),
            lineValidator = get(named("lineValidator")),
            emptyFieldValidator = get(named("emptyFieldValidator"))
        )
    }
}
