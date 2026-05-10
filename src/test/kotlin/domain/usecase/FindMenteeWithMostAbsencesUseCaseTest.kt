package domain.usecase

import kotlinx.coroutines.test.runTest

import com.google.common.truth.Truth
import data.BaseKoinTest
import data.fixture.TestDataFactory
import di.testModule
import domain.model.entity.AttendanceStatus
import domain.model.exception.ValidationExeption
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("FindMenteeWithMostAbsencesUseCase")
class FindMenteeWithMostAbsencesUseCaseTest : BaseKoinTest() {

    private val findMenteeWithMostAbsences:
            FindMenteeWithMostAbsencesUseCase by lazy { resolve() }

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `returns success when mentee with most absences exists`() = runTest {
        // Given: m01 has one absence, m02 and m03 are present
        val expectedMenteeId = "m01"

        // When: finding the mentee with most absences
        val result = findMenteeWithMostAbsences()

        // Then: result is success with the mentee who has the most absences
        Truth.assertThat(result.getOrThrow().id).isEqualTo(expectedMenteeId)
    }

    @Test
    fun `returns success with null when no absences exist`() = runTest {
        // Given: all mentees are present with no absences recorded
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
        )

        // When: finding the mentee with most absences
        val result = findMenteeWithMostAbsences()

        // Then: result is success with null since no absences exist
        assertIs<ValidationExeption.DataNotFoundExeption>(result.exceptionOrNull())
    }

    @Test
    fun `returns success with null when attendance list is empty`() = runTest {
        // Given: no attendance records exist in the system
        TestDataFactory.currentAttendances = emptyList()

        // When: finding the mentee with most absences
        val result = findMenteeWithMostAbsences()

        // Then: result is success with null since no records exist
        assertIs<ValidationExeption.DataNotFoundExeption>(result.exceptionOrNull())
    }

    @Test
    fun `returns success with mentee who has most absences when others have late status`() = runTest {
        // Given: m01 has one absence, m02 has late status which does not count as absence
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.LATE),
            TestDataFactory.attendance("m02", 2, AttendanceStatus.LATE),
        )

        // When: finding the mentee with most absences
        val result = findMenteeWithMostAbsences()

        // Then: result is success with m01 since late does not count as absence
        val studentHighestNumberOfAbsences = "m01"
        Truth.assertThat(result.getOrThrow().id).isEqualTo(studentHighestNumberOfAbsences)
    }

    @Test
    fun `returns success with mentee who has most absences across multiple weeks`() = runTest {
        // Given: m01 is absent in three weeks, m02 is absent in one week only
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m01", 2, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m01", 3, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.ABSENT),
        )

        // When: finding the mentee with most absences
        val result = findMenteeWithMostAbsences()

        // Then: result is success with m01 since they have the highest absence count
        val studentHighestNumberOfAbsences = "m01"
        Truth.assertThat(result.getOrThrow().id).isEqualTo(studentHighestNumberOfAbsences)
    }

}