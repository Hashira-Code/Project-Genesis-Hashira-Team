package domain

data class Team(
    val id: String,
    val name: String,
    val mentorLead: String,
    val mentees: MutableList<Mentee> = mutableListOf()
)
