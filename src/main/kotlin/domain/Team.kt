package domain

data class Team(
    val id: String,
    val teamName: String,
    val mentorLead: String,
    val mentees: MutableList<Mentee> = mutableListOf()
)