package support

import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType
import domain.repository.PerformanceRepo

class FakePerformanceRepo(
    private val Submissions: List<PerformanceSubmission> = emptyList()
) : PerformanceRepo {
    override fun getAll(): Result<List<PerformanceSubmission>> = Result.success(Submissions)

    override fun getByMenteeId(menteeId: String): Result<List<PerformanceSubmission>> =
        Result.success(Submissions.filter { it.menteeId == menteeId })

    override fun getByType(type: SubmissionType): Result<List<PerformanceSubmission>> =
        Result.success(Submissions.filter { it.type == type })
}