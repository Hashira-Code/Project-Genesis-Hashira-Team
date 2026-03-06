package domain.repository

import domain.model.PerformanceSubmission
import domain.model.SubmissionType

interface PerformanceRepo {
    fun getAll(): Result<List<PerformanceSubmission>>
    fun getByMenteeId(menteeId: String): Result<List<PerformanceSubmission>>
    fun getByType(type: SubmissionType): Result<List<PerformanceSubmission>>

}
