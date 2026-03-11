package data.mapper

import data.model.MenteeRaw
import domain.model.entity.Mentee

class MenteeMapper() : Mapper<MenteeRaw, Mentee> {

    override fun toDomain(rawList: List<MenteeRaw>): List<Mentee> {
        return rawList.map { raw ->
            Mentee.create(
                id = raw.id,
                name = raw.name,
                teamId = raw.teamId
            )
        }
    }
}




