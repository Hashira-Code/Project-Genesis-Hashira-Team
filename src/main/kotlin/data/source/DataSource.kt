package data.source

import data.model.AttendanceRaw
import data.model.MenteeRaw
import data.model.PerformanceRaw
import data.model.ProjectRaw
import data.model.TeamRaw

interface DataSource {
    fun getAllMentees():List<MenteeRaw>
    fun getAllTeams():List<TeamRaw>
    fun getAllProjects():List<ProjectRaw>
    fun getAllAttendance():List<AttendanceRaw>
    fun getAllPerformance():List<PerformanceRaw>


}