package data.mapper

import data.model.ProjectRaw
import domain.model.Project

class ProjectMapper:Mapper<ProjectRaw, Project> {
    override fun toDomain(rawList: List<ProjectRaw>): List<Project> {
      return rawList.map{raw ->
          Project(
              id=raw.id,
              name=raw.name,
              teamId = raw.teamId
          )
      }
    }
}