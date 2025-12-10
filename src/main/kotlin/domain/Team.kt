package domain

data class Team(
    val id: String,
    val Name: String,
    val mentorLead: String,
    val mentees: MutableList<Mentee> = mutableListOf()
)
