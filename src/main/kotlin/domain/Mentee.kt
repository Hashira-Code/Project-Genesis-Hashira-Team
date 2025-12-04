package domain

data class Mentee(
    val menteeId: String,
    val name: String,
    val teamId: String
) {
    var team: Team? = null
    var submissions : List<PerformanceSubmission> = emptyList()


}