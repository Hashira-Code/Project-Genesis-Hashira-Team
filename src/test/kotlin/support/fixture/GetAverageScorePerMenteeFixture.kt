package support.fixture

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType

object GetAverageScorePerMenteeFixture {
    data class Case(
        val name: String,
        val mentees: List<Mentee>,
        val submissions: List<PerformanceSubmission>,
        val expectedAverages: List<Pair<String, Double>>
    )

    val multipleSubmissionsPerMentee = Case(
        name = "calculates average score per mentee and preserves mentee order",
        mentees = TestDataFactory.defaultMentees(),
        submissions = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 80.0),
            TestDataFactory.submission("s02", "m01", SubmissionType.BOOK_CLUB, 100.0),
            TestDataFactory.submission("s03", "m02", SubmissionType.TASK, 70.0),
            TestDataFactory.submission("s04", "m03", SubmissionType.WORKSHOP, 60.0)
        ),
        expectedAverages = listOf(
            "Aisha" to 90.0,
            "Sara" to 70.0,
            "Lina" to 60.0
        )
    )

    val menteesWithoutSubmissionsAreExcluded = Case(
        name = "excludes mentees who have no submissions",
        mentees = TestDataFactory.defaultMentees(),
        submissions = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 80.0)
        ),
        expectedAverages = listOf("Aisha" to 80.0)
    )

    val emptySubmissionListReturnsEmptyResult = Case(
        name = "returns empty list when there are no submissions",
        mentees = TestDataFactory.defaultMentees(),
        submissions = emptyList(),
        expectedAverages = emptyList()
    )

    val cases = listOf(
        multipleSubmissionsPerMentee,
        menteesWithoutSubmissionsAreExcluded,
        emptySubmissionListReturnsEmptyResult
    )
}
