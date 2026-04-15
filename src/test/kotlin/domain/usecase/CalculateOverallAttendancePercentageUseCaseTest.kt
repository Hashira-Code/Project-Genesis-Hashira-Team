package domain.usecase

import org.junit.jupiter.api.Test
import data.BaseKoinTest
import di.testModule
import data.fixture.TestDataFactory
import domain.model.entity.AttendanceStatus
import domain.model.request.MenteeIdRequest
import kotlin.test.assertEquals

class CalculateOverallAttendancePercentageUseCaseTest:BaseKoinTest()   {

    private val CalculateOverallAttendancePercentage: CalculateOverallAttendancePercentageUseCase by lazy { resolve() }

    override fun setup() {
        startKoinWith(testModule)
    }


    @Test
    fun `should return correct percentage for mentee m01`(){
        //Given
        TestDataFactory.currentAttendances=listOf(
            TestDataFactory.attendance("m01", 1, AttendanceStatus.ABSENT),
            TestDataFactory.attendance("m01", 2, AttendanceStatus.LATE),
            TestDataFactory.attendance("m01", 3, AttendanceStatus.PRESENT)
        )

        //when
        val result=CalculateOverallAttendancePercentage(MenteeIdRequest("m01"))

        //Then
        assertEquals(50.0, result.getOrNull())
    }
}

