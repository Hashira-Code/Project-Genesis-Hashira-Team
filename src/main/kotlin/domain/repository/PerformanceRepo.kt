package domain.repository

import domain.model.PerformanceSubmission
import domain.model.SubmissionType

interface PerformanceRepo {
    fun getAll(): List<PerformanceSubmission>


}
