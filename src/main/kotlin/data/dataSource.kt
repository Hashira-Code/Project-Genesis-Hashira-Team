package data

import dataRaw.menteeRaw
import dataRaw.projectRaw
import dataRaw.teamRaw
import data.model.*

interface dataSource {
    fun getAllMentees():List<menteeRaw>
    fun getAllTeams():List<teamRaw>
    fun getAllProjects():List<projectRaw>
    fun getAllAttendance():List<AttendanceRaw>
    fun getAllPerformance():List<PerformanceRaw>


}