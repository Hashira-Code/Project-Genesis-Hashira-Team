package data

import dataRaw.menteeRaw
import dataRaw.projectRaw
import dataRaw.teamRaw
import java.io.File

class csvDataSource(val path:String): dataSource {
    private val rawList= File(path).readLines().drop(1).map { Raw ->
        Raw.split(",")
    }
    override fun getAllMentees(): List<menteeRaw> {
       return menteeParse()
    }

    override fun getAllTeams(): List<teamRaw> {
        return teamParse()
    }

    override fun getAllProjects(): List<projectRaw> {
        return projectParse()
    }

    private fun menteeParse():List<menteeRaw>{
         return rawList.map{Raw ->
             menteeRaw(
                 id=Raw[0].trim(),
                 name=Raw[1].trim(),
                 teamId=Raw[2].trim()
             )
         } }

    private fun teamParse():List<teamRaw>{
        return rawList.map{Raw ->
            teamRaw(
                id=Raw[0].trim(),
                name=Raw[1].trim(),
                mentorLead = Raw[2].trim()
            )
        } }

    private fun projectParse():List<projectRaw>{
        return rawList.map{Raw ->
            projectRaw(
                id=Raw[0].trim(),
                name=Raw[1].trim(),
                teamId=Raw[2].trim()
            )
        } }
}