package domain.repository

import domain.model.PerformanceSubmission

interface PerformanceRepository {
    fun getAll(): List<PerformanceSubmission>
    fun getByMenteeId(menteeId: String): List<PerformanceSubmission>
}
