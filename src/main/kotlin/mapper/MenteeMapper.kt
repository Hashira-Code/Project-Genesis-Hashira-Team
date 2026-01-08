package mapper

import dataRaw.menteeRaw
import domain.model.Mentee

class MenteeMapper(): Mapper <menteeRaw,Mentee>{

    override fun toDaomain(rawList: List<menteeRaw>): List<Mentee> {
        return rawList.map{Raw ->
            Mentee(
                id=Raw.id,
                name=Raw.name,
                teamId=Raw.teamId
            )
        } }
}


