package domain.usecase

import com.google.common.truth.Truth.assertThat
import data.BaseKoinTest
import data.fixture.TestDataFactory
import di.testModule
import domain.model.exception.ValidationExeption
import domain.model.request.MenteeIdRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

@DisplayName("FindLeadMentorForMenteeUseCase")
class FindLeadMentorForMenteeUseCaseTest : BaseKoinTest() {

    private val findLeadMentorForMentee: FindLeadMentorForMenteeUseCase by lazy { resolve() }

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `returns success with lead mentor name when mentee and team exist`() {
        // Given: default data where mentee "m01" belongs to team "alpha"

        // When: finding the lead mentor for mentee "m01"
        val result = findLeadMentorForMentee(MenteeIdRequest("m01"))

        // Then: the result should be success and return Alpha team's lead mentor
        assertThat(result.getOrThrow()).isEqualTo("Sarah")
    }

    @Test
    fun `returns success with lead mentor name for mentee in another team`() {
        // Given: default data where mentee "m03" belongs to team "beta"

        // When: finding the lead mentor for mentee "m03"
        val result = findLeadMentorForMentee(MenteeIdRequest("m03"))

        // Then: the result should be success and return Beta team's lead mentor
        assertThat(result.getOrThrow()).isEqualTo("Omar")
    }

    @Test
    fun `returns failure with EntityNotFoundExeption when mentee does not exist`() {
        // Given: no mentee exists with id "m99"

        // When: finding the lead mentor for a non-existent mentee
        val result = findLeadMentorForMentee(MenteeIdRequest("m99"))

        // Then: the result should be failure with EntityNotFoundExeption
        assertIs<ValidationExeption.EntityNotFoundExeption>(result.exceptionOrNull())
    }

    @Test
    fun `returns failure with EntityNotFoundExeption when mentee team does not exist`() {
        // Given: mentee "m01" belongs to a team that does not exist
        TestDataFactory.currentMentees = listOf(
            TestDataFactory.mentee(id = "m01", name = "Aisha", teamId = "missing-team")
        )

        // When: finding the lead mentor for mentee "m01"
        val result = findLeadMentorForMentee(MenteeIdRequest("m01"))

        // Then: the result should be failure with EntityNotFoundExeption
        assertIs<ValidationExeption.EntityNotFoundExeption>(result.exceptionOrNull())
    }
}
