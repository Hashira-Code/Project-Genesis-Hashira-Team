package data.fixture

import domain.model.entity.Attendance
import domain.model.entity.AttendanceStatus
import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.Project
import domain.model.entity.SubmissionType
import domain.model.entity.Team

object TestDataFactory {
    var currentAttendances = defaultAttendances()
    var currentPerformances = defaultPerformanceSubmissions()
    var currentProjects = defaultProjects()
    var currentTeams = defaultTeams()
    var currentMentees = defaultMentees()

    fun reset() {
        currentAttendances = defaultAttendances()
        currentPerformances = defaultPerformanceSubmissions()
        currentProjects = defaultProjects()
        currentTeams = defaultTeams()
        currentMentees = defaultMentees()
    }

    fun team(
        id: String = "alpha",
        name: String = "Alpha Team",
        mentorLead: String = "Sarah"
    ) = Team.create(id, name, mentorLead)

    fun project(
        id: String = "p01",
        name: String = "Helios Initiative",
        teamId: String = "alpha"
    ) = Project.create(id, name, teamId)

    fun mentee(
        id: String = "m01",
        name: String = "Aisha",
        teamId: String = "alpha"
    ) = Mentee.create(id, name, teamId)

    fun attendance(
        menteeId: String,
        weekNumber: Int,
        status: AttendanceStatus
    ) = Attendance(menteeId, weekNumber, status)

    fun submission(
        id: String,
        menteeId: String,
        type: SubmissionType,
        score: Double
    ) = PerformanceSubmission(id, menteeId, type, score)

    fun defaultTeams() = listOf(
        team(),
        team(id = "beta", name = "Beta Team", mentorLead = "Omar")
    )

    fun defaultProjects() = listOf(
        project(),
        project(id = "p02", name = "Nova Platform", teamId = "beta")
    )

    fun defaultMentees() = listOf(
        mentee(),
        mentee(id = "m02", name = "Sara"),
        mentee(id = "m03", name = "Lina", teamId = "beta")
    )

    fun defaultAttendances() = listOf(
        attendance("m01", 1, AttendanceStatus.ABSENT),
        attendance("m02", 1, AttendanceStatus.PRESENT),
        attendance("m03", 1, AttendanceStatus.PRESENT)
    )

    fun defaultPerformanceSubmissions() = listOf(
        submission("s01", "m01", SubmissionType.TASK, 95.0),
        submission("s02", "m02", SubmissionType.TASK, 80.0),
        submission("s03", "m03", SubmissionType.BOOK_CLUB, 99.0),
        submission("s04", "m02", SubmissionType.TASK, -10.0)
    )
}