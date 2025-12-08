package domain

data class Mentee(
    val Id: String,
    val name: String,
    val teamId: String
) {
    private var assignedTeam: Team? = null
    val team: Team?
        get() = assignedTeam

    var submissions = mutableListOf<PerformanceSubmission>()
    fun getSubmissions(): List<PerformanceSubmission> = submissions

    fun assignTeam(team: Team?) {
        assignedTeam = team
    }

    fun addSubmission(submission: PerformanceSubmission) {
        submissions.add(submission)

    }
}