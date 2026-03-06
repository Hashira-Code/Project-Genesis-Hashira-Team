package data.repository

import data.mapper.Mapper
import data.model.PerformanceRaw
import data.dataSource.DataSource
import domain.model.PerformanceSubmission
import domain.model.SubmissionType
import domain.repository.PerformanceRepo


class PerformanceRepoImpl(
    private val dataSource: DataSource,
    private val mapper: Mapper<PerformanceRaw, PerformanceSubmission>
) : PerformanceRepo {

    private val cache: Result<List<PerformanceSubmission>> by lazy {
        runCatching { mapper.toDomain(dataSource.getAllPerformance()) }
    }

    private val byMenteeId: Map<String, List<PerformanceSubmission>> by lazy {
        cache.getOrDefault(emptyList()).groupBy { it.menteeId }
    }

    private val byType: Map<SubmissionType, List<PerformanceSubmission>> by lazy {
        cache.getOrDefault(emptyList()).groupBy { it.type }
    }

    override fun getAll(): Result<List<PerformanceSubmission>> = cache

    override fun getByMenteeId(menteeId: String): Result<List<PerformanceSubmission>> =
        cache.map { byMenteeId[menteeId].orEmpty() }

    override fun getByType(type: SubmissionType): Result<List<PerformanceSubmission>> =
        cache.map { byType[type].orEmpty() }

}
