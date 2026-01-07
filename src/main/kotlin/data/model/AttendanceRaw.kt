package data.model

data class AttendanceRaw(
    val menteeId: String,
    val week : Map<Int,String>
)
