package data

import data.fixture.TestDataFactory
import di.defaultTestModules
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

abstract class BaseKoinTest : KoinComponent {

    @BeforeEach
    fun setUpKoin() {
        TestDataFactory.reset()
        startKoin {
            modules(defaultTestModules)
        }
    }

    @AfterEach
    fun tearDownKoin() {
        stopKoin()
    }
}
