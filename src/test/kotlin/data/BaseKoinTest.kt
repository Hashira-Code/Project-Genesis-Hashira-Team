package data

import org.junit.jupiter.api.AfterEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
 development
import org.koin.core.component.get
 main
import org.koin.core.module.Module

abstract class BaseKoinTest : KoinComponent {

 development

    @BeforeEach
    abstract fun setup()

    protected fun startKoinWith(modules: Module){
        stopKoin()
        startKoin { modules(modules) }

    protected fun startKoinWith(
        vararg modules: Module
    ) {
        stopKoin()
        startKoin {
            modules(modules.toList())
        }
 main
    }

    protected inline fun <reified T> resolve(): T = get()

    @AfterEach
    fun tearDownKoin() {
        stopKoin()
    }
}