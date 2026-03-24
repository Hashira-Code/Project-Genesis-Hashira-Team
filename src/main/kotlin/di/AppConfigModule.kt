package di

import org.koin.dsl.module

data class AppConfig(
    val resourcesPath: String
)
{
    companion object {
        const val DEFAULT_RESOURCES_PATH = "src/main/resources"
    }
}

val configModule = module {
    single { AppConfig(resourcesPath = AppConfig.DEFAULT_RESOURCES_PATH) }
}
