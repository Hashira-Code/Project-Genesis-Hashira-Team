package data

import dataRaw.menteeRaw
import dataRaw.projectRaw
import dataRaw.teamRaw

interface dataSource {
    fun getAllMentees():List<menteeRaw>
    fun getAllTeams():List<teamRaw>
    fun getAllProjects():List<projectRaw>


}