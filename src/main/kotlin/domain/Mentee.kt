package domain

data class Mentee(
    val Id: String,
    val name: String,
    val teamId: String,
    val  submissions: MutableList<PerformanceSubmission> = mutableListOf(),
    var team: Team? = null
) 