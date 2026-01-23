package data.repository
import data.dataSource
import domain.model.PerformanceSubmission
import domain.model.SubmissionType
import domain.repository.PerformanceRepo
import mapper.Mapper
import dataRaw.PerformanceRaw

class PerformanceRepoImply(
    private val dataSource: dataSource,
    private val mapper: Mapper<PerformanceRaw, PerformanceSubmission>
) : PerformanceRepo {

    private val cache: List<PerformanceSubmission> by lazy {
        mapper.toDomain(dataSource.getAllPerformance())
    }

    private val byMenteeId: Map<String, List<PerformanceSubmission>> by lazy {
        cache.groupBy { it.menteeId }
    }

    private val byType: Map<SubmissionType, List<PerformanceSubmission>> by lazy {
        cache.groupBy { it.type }
    }

    override fun getAll(): List<PerformanceSubmission> = cache

    override fun getByMenteeId(menteeId: String): List<PerformanceSubmission> =
        byMenteeId[menteeId].orEmpty()

    override fun getByType(type: SubmissionType): List<PerformanceSubmission> =
        byType[type].orEmpty()

}
