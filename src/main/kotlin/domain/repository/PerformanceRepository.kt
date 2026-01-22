package domain.repository

import domain.model.PerformanceSubmission
import domain.model.SubmissionType

interface PerformanceRepository {
    fun getAll(): List<PerformanceSubmission>
    fun getByMenteeId(menteeId: String): List<PerformanceSubmission>
    fun getByType(type: SubmissionType): List<PerformanceSubmission>
}
