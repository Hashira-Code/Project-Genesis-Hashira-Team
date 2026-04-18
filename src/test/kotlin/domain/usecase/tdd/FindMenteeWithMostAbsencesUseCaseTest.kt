package domain.usecase.tdd

import com.google.common.truth.Truth.assertThat
import data.BaseKoinTest
import data.fixture.TestDataFactory
import di.testModule
import domain.model.entity.AttendanceStatus
import domain.usecase.FindMenteeWithMostAbsencesUseCase
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

@DisplayName("FindMenteeWithMostAbsencesUseCase")
class FindMenteeWithMostAbsencesUseCaseTest : BaseKoinTest() {

    private val findMenteeWithMostAbsences: FindMenteeWithMostAbsencesUseCase by lazy { resolve() }

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `returns success when mentee with most absences exists`() {
        // Given: m01 has one absence, m02 and m03 are present
        val expectedMenteeId = "m01"

        // When: finding the mentee with most absences
        val result = findMenteeWithMostAbsences()

        // Then: result is success with the mentee who has the most absences
        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()?.id).isEqualTo(expectedMenteeId)
    }

    @Test
    fun `returns success with null when no absences exist`() {
        // Given: all mentees are present with no absences recorded
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.PRESENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.PRESENT),
        )

        // When: finding the mentee with most absences
        val result = findMenteeWithMostAbsences()

        // Then: result is success with null since no absences exist
        assertThat(result.getOrNull()).isNull()
    }

    @Test
    fun `returns success with null when attendance list is empty`() {
        // Given: no attendance records exist in the system
        TestDataFactory.currentAttendances = emptyList()

        // When: finding the mentee with most absences
        val result = findMenteeWithMostAbsences()

        // Then: result is success with null since no records exist
        assertThat(result.getOrNull()).isNull()
    }

    @Test
    fun `returns success with mentee who has most absences when others have late status`() {
        // Given: m01 has one absence, m02 has late status which does not count as absence
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m02", 1, AttendanceStatus.LATE),
            TestDataFactory.attendance("m02", 2, AttendanceStatus.LATE),
        )

        // When: finding the mentee with most absences
        val result = findMenteeWithMostAbsences()

        // Then: result is success with m01 since late does not count as absence
        assertThat(result.getOrNull()?.id).isEqualTo("m01")
    }

    @Test
    fun `returns success with mentee who has most absences across multiple weeks`() {
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
        assertThat(result.getOrNull()?.id).isEqualTo("m01")
    }

}