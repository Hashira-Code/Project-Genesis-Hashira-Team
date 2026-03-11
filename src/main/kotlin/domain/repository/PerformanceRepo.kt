package domain.repository

import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType

interface PerformanceRepo {
    fun getAll(): Result<List<PerformanceSubmission>>
    fun getByMenteeId(menteeId: String): Result<List<PerformanceSubmission>>
    fun getByType(type: SubmissionType): Result<List<PerformanceSubmission>>

}


