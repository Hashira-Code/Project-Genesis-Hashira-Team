package domain.usecase.unit.test

import domain.usecase.EvaluateTeamHealthUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import testModules
import kotlin.test.assertTrue
import com.google.common.truth.Truth.assertThat
import domain.model.entity.TeamHealthStatus

@DisplayName("EvaluateTeamHealthUseCase")
class EvaluateTeamHealthUseCaseTest : KoinComponent {

    private val evaluateTeamHealth by inject<EvaluateTeamHealthUseCase>()

    @BeforeEach
    fun setUp(){
        stopKoin()
        startKoin {
            modules(testModules,module { single { EvaluateTeamHealthUseCase(get(),get(),get(),get()) } })
        }
    }
    @AfterEach
    fun tearDown(){
        stopKoin()
    }


   @Test
   fun `should return EXCELLENT when both performance and attendance are above thresholds`() {
       val result = evaluateTeamHealth()
       assertTrue(result.isSuccess)
       val report = result.getOrNull()
       assertThat(report).isNotNull()
       val status = report!!["Alpha Team"]
       assertThat(status).isEqualTo(TeamHealthStatus.EXCELLENT)
   }
    @Test
    fun `should return GOOD when performance is average and attendance is acceptable`(){
        val  result = evaluateTeamHealth()
        val status = result.getOrNull()!!["Alpha Team"]
        assertThat(status).isEqualTo(TeamHealthStatus.GOOD)
    }
    @Test
    fun `should return NEEDS_ATTENTION when performance falls below threshold`() {
        val result = evaluateTeamHealth()
        val status = result.getOrNull()!!["Alpha Team"]
        assertThat(status).isEqualTo(TeamHealthStatus.NEEDS_ATTENTION)

    }
    @Test
    fun `should return failure when TeamRepo fails to fetch teams`(){
        val result= evaluateTeamHealth()
        assertTrue(result.isFailure)
    }
}