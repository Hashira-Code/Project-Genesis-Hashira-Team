package data.repository

import data.mapper.Mapper
import data.model.PerformanceRaw
import data.dataSource.DataSource
import data.exception.mapCsvErrorToDomain
import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType
import domain.repository.PerformanceRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PerformanceRepoImpl(
    private val dataSource: DataSource,
    private val mapper: Mapper<PerformanceRaw, PerformanceSubmission>
) : PerformanceRepo {

    private val cache: Result<List<PerformanceSubmission>> by lazy {
        runCatching { mapper.toDomain(dataSource.getAllPerformance()) }
            .mapCsvErrorToDomain()
    }

    private val byMenteeId: Map<String, List<PerformanceSubmission>> by lazy {
        cache.getOrThrow().groupBy { it.menteeId }
    }

    private val byType: Map<SubmissionType, List<PerformanceSubmission>> by lazy {
        cache.getOrThrow().groupBy { it.type }
    }

    override suspend fun getAll(): Result<List<PerformanceSubmission>> = withContext(Dispatchers.IO) { cache }

    override suspend fun getByMenteeId(menteeId: String): Result<List<PerformanceSubmission>> =
        withContext(Dispatchers.IO) {
            cache.map { byMenteeId[menteeId].orEmpty() }
        }

    override suspend fun getByType(type: SubmissionType): Result<List<PerformanceSubmission>> =
        withContext(Dispatchers.IO) {
            cache.map { byType[type].orEmpty() }
        }

}


