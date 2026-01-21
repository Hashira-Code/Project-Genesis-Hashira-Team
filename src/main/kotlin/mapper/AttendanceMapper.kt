package mapper
import dataRaw.AttendanceRaw
import domain.model.Attendance
import domain.model.AttendanceStatus
class AttendanceMapper  : Mapper <AttendanceRaw,Attendance>{
    override fun toDomain(rawList:List<AttendanceRaw>):List<Attendance> {
        return rawList.flatMap {raw ->
            raw.weeks.mapIndexed { index, status ->
                Attendance(
                     raw.menteeId,
                    index+1,
                    status.toAttendanceStatus()



                )
            }

        }


        }


    }

fun String.toAttendanceStatus() : AttendanceStatus {
    return runCatching { AttendanceStatus.valueOf(this.trim().uppercase()) }
        .getOrElse { AttendanceStatus.UNKNOWN }
}