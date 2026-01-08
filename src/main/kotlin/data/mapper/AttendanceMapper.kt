package data.mapper

import data.model.AttendanceRaw
import domain.model.Attendance
import  domain.model.AttendanceStatus

class AttendanceMapper {
    fun attendanceMapper(raws: List<AttendanceRaw>): List<Attendance> {
        return raws.flatMap { raw ->
            raw.weeks.mapIndexed { index, statusString ->
                Attendance(
                    raw.menteeId,
                    index + 1,
                    statusString.toAttendanceStatus(),
                )
            }
        }
    }
}

fun String.toAttendanceStatus(): AttendanceStatus {
    return runCatching { AttendanceStatus.valueOf(this.trim().uppercase()) }
        .getOrElse { AttendanceStatus.UNKNOWN }
}

