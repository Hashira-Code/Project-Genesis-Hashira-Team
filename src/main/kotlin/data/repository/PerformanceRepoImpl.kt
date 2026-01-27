package data.repository
import data.mapper.Mapper
import data.model.PerformanceRaw
import data.source.DataSource
import domain.model.PerformanceSubmission
import domain.model.SubmissionType
import domain.repository.PerformanceRepo


class PerformanceRepoImpl(
    private val dataSource: DataSource,
    private val mapper: Mapper<PerformanceRaw, PerformanceSubmission>
) : PerformanceRepo {

    private val cache: List<PerformanceSubmission> by lazy {
        mapper.toDomain(dataSource.getAllPerformance())
    }


    override fun getAll(): List<PerformanceSubmission> = cache



}
