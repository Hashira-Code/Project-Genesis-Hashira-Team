package domain.repository

import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType

interface PerformanceRepo {
    suspend fun getAll(): Result<List<PerformanceSubmission>>
    suspend fun getByMenteeId(menteeId: String): Result<List<PerformanceSubmission>>
    suspend fun getByType(type: SubmissionType): Result<List<PerformanceSubmission>>

}


