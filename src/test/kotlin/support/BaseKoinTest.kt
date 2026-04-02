package support

import org.junit.jupiter.api.AfterEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

abstract class BaseKoinTest : KoinComponent {

    protected fun startKoinWith(
        vararg modules: Module
    ) {
        stopKoin()
        startKoin {
            modules(modules.toList())
        }
    }


    protected inline fun <reified T> resolve(): T = get()

    @AfterEach
    fun tearDownKoin() {
        stopKoin()
    }
}
