package data

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

abstract class BaseKoinTest : KoinComponent {

    @BeforeEach
    abstract fun setup()

    protected fun startKoinWith(testModules: Module) {
        stopKoin()
        startKoin { modules(testModules) }
    }

    protected inline fun <reified T> resolve(): T = get()

    @AfterEach
    fun tearDownKoin() {
        stopKoin()
    }
}