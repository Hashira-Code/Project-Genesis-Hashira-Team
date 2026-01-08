package mapper

import dataRaw.projectRaw
import domain.model.Project

class ProjectMapper:Mapper<projectRaw, Project> {
    override fun toDaomain(rawList: List<projectRaw>): List<Project> {
      return rawList.map{Raw ->
          Project(
              id=Raw.id,
              name=Raw.name,
              teamId = Raw.teamId
          )
      }
    }
}