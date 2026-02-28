package data.mapper
import data.model.AttendanceRaw
import domain.model.Attendance
import domain.model.AttendanceStatus
class AttendanceMapper  : Mapper <AttendanceRaw,Attendance>{
    override fun toDomain(rawList:List<AttendanceRaw>):List<Attendance> {
        return rawList.flatMap {raw ->
            raw.weeks.mapIndexed { index, status ->
                Attendance(
                    menteeId = raw.menteeId,
                    weekNumber = index+1,
                    status = status.toAttendanceStatus()



                )
            }

        }


        }


    }

private fun String.toAttendanceStatus() : AttendanceStatus =
    when (this.trim().uppercase()) {
        "PRESENT" -> AttendanceStatus.PRESENT
        "ABSENT" -> AttendanceStatus.ABSENT
        "LATE" -> AttendanceStatus.LATE
        else -> throw IllegalArgumentException("Invalid attendance status: $this")
    }
