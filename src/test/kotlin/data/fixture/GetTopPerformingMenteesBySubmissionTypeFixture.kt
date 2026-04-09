package data.fixture

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType

object GetTopPerformingMenteesBySubmissionTypeFixture {
    data class Case(
        val name: String,
        val submissions: List<PerformanceSubmission>,
        val mentees: List<Mentee>,
        val submissionType: SubmissionType,
        val expectedMenteeId: String? = null
    )
    val highestValidTaskScoreWins = Case(
        name = "highest valid task score wins and negative scores are ignored",
        submissions = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 95.0),
            TestDataFactory.submission("s02", "m02", SubmissionType.TASK, 80.0),
            TestDataFactory.submission("s03", "m03", SubmissionType.BOOK_CLUB, 99.0),
            TestDataFactory.submission("s04", "m02", SubmissionType.TASK, -10.0)
        ),
        mentees = TestDataFactory.defaultMentees(),
        submissionType = SubmissionType.TASK,
        expectedMenteeId = "m01"
    )

    val tiedScoresReturnFirstEncounteredMentee = Case(
        name = "first matching mentee wins when scores are tied",
        submissions = listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 90.0),
            TestDataFactory.submission("s02", "m02", SubmissionType.TASK, 90.0),
            TestDataFactory.submission("s03", "m03", SubmissionType.BOOK_CLUB, 70.0)
        ),
        mentees = TestDataFactory.defaultMentees(),
        submissionType = SubmissionType.TASK,
        expectedMenteeId = "m01"
    )

    val noMatchingSubmissionType = Case(
        name = "null is returned when no submission matches the requested type",
        submissions = listOf(
            TestDataFactory.submission("s01", "m03", SubmissionType.BOOK_CLUB, 85.0)
        ),
        mentees = TestDataFactory.defaultMentees(),
        submissionType = SubmissionType.TASK,
        expectedMenteeId = null
    )
}