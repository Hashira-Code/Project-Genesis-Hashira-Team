package domain.usecase

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import data.BaseKoinTest
import di.testModule
import data.fixture.TestDataFactory
import domain.model.entity.AttendanceStatus
import domain.model.request.MenteeIdRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CalculateOverallAttendancePercentageUseCaseTest : BaseKoinTest() {

    private val calculateOverallAttendancePercentageUseCase:
            CalculateOverallAttendancePercentageUseCase by lazy { resolve() }

    override fun setup() {
        startKoinWith(testModule)
    }

    @Test
    fun `should return correct percentage for mentee m01`() = runTest {

        // Given: attendance records for mentee m01 with different attendance statuses
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m01", 2, AttendanceStatus.LATE),
            TestDataFactory.attendance("m01", 3, AttendanceStatus.PRESENT)
        )

        // When: calculating overall attendance percentage for mentee m01
        val result =
            calculateOverallAttendancePercentageUseCase(MenteeIdRequest("m01"))

        // Then: should return 50% attendance percentage for mentee m01
        assertEquals(50.0, result.getOrNull())
    }

    @Test
    fun `should return -1 when mentee does not exist`() = runTest {

        // Given: attendance records that do not belong to mentee m03
        TestDataFactory.currentAttendances = listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m01", 2, AttendanceStatus.LATE),
            TestDataFactory.attendance("m02", 3, AttendanceStatus.PRESENT)
        )

        // When: calculating overall attendance percentage for non existing mentee
        val result =
            calculateOverallAttendancePercentageUseCase(MenteeIdRequest("m03"))

        // Then: should return -1 because the mentee has no attendance records
        assertEquals(-1.0, result.getOrNull())
    }
}