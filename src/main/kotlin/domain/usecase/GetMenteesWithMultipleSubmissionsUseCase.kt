package domain.usecase
import domain.repository.MenteeRepo
import domain.repository.PerformanceRepo

class GetMenteesWithMultipleSubmissionsUseCase(
    private val menteeRepo: MenteeRepo,
    private val performanceRepo: PerformanceRepo
) {
    operator fun invoke(): List<String> {
        val mentees = menteeRepo.getAll()
        val submissions = performanceRepo.getAll()
        val menteeIdsWithMultipleSubmissions =
            submissions
                .groupingBy { it.menteeId }
                .eachCount()
                .filter { it.value > 1 }
                .keys
        return mentees
            .filter { it.id in menteeIdsWithMultipleSubmissions }
            .map { it.name }
    }
}