package domain

data class Team(
    val Id: String,
    val teamName: String,
    val mentorLead: String,
    val mentees: MutableList<Mentee> = mutableListOf()
){
    fun addMentee(mentee: Mentee){
        mentees.add(mentee)
    }
}