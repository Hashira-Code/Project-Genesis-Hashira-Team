package domain

data class Team(
    val teamId: String,
    val teamName: String,
    val mentorLead: String,
    val mentees: MutableList<Mentee> = mutableListOf()
)