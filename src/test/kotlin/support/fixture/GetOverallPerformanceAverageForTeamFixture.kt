package support.fixture

import domain.model.entity.Mentee
import domain.model.entity.PerformanceSubmission
import domain.model.entity.SubmissionType

object GetOverallPerformanceAverageForTeamFixture {
    data class Case(
        val name: String,
        val mentees: List<Mentee>,
        val submissions: List<PerformanceSubmission>,
        val requestTeamId: String,
        val expectedAverage: Double? = null,
        val expectedErrorMessage: String? = null
    )

    val teamWithScoredMentees = Case(
        "returns average score for all submissions of the requested team",
         TestDataFactory.defaultMentees(),
         listOf(
            TestDataFactory.submission("s01", "m01", SubmissionType.TASK, 90.0),
            TestDataFactory.submission("s02", "m02", SubmissionType.TASK, 80.0),
            TestDataFactory.submission("s03", "m03", SubmissionType.TASK, 70.0)
        ),
         "alpha",
         85.0
    )

    val teamWithNoMentees = Case(
         "fails when the requested team has no mentees",
         TestDataFactory.defaultMentees(),
         TestDataFactory.defaultPerformanceSubmissions(),
         "gamma",
        expectedErrorMessage = "No mentees found for this team"
    )

    val teamWithMenteesButNoSubmissions = Case(
        "returns zero when team mentees have no submissions",
         TestDataFactory.defaultMentees(),
         listOf(
            TestDataFactory.submission("s03", "m03", SubmissionType.TASK, 70.0)
        ),
         "alpha",
        0.0
    )

    val cases = listOf(teamWithScoredMentees, teamWithNoMentees, teamWithMenteesButNoSubmissions)
}