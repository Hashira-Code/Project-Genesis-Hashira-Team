package data.fake

import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType
import domain.repository.PerformanceRepo

class FakePerformanceRepo(
    private val submissions: List<PerformanceSubmission> = emptyList()
) : PerformanceRepo {
    override suspend fun getAll(): Result<List<PerformanceSubmission>> = Result.success(submissions)

    override suspend fun getByMenteeId(menteeId: String): Result<List<PerformanceSubmission>> =
        Result.success(submissions.filter { it.menteeId == menteeId })

    override suspend fun getByType(type: SubmissionType): Result<List<PerformanceSubmission>> =
        Result.success(submissions.filter { it.type == type })
}