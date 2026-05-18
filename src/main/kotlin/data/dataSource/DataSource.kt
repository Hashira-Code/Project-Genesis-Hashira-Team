package data.dataSource

import data.model.AttendanceRaw
import data.model.MenteeRaw
import data.model.PerformanceRaw
import data.model.ProjectRaw
import data.model.TeamRaw

interface DataSource {
    suspend fun getAllMentees(): List<MenteeRaw>
    suspend fun getAllTeams(): List<TeamRaw>
    suspend fun getAllProjects(): List<ProjectRaw>
    suspend fun getAllAttendance(): List<AttendanceRaw>
    suspend fun getAllPerformance(): List<PerformanceRaw>
}