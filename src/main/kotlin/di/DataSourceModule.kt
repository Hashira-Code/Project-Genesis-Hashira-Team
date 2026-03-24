package di

import data.dataSource.CsvDataSource
import data.dataSource.DataSource
import org.koin.dsl.module

val dataSourceModule = module {

    single<DataSource> {
        val config = get<AppConfig>()
        CsvDataSource(
            path = config.resourcesPath,
            fileValidator = get(),
            lineValidator = get(),
            emptyFieldValidator = get()
        )
    }
}