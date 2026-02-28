package domain.validation

import domain.exception.ScoreValidationException

class ScoreValidator : Validator<Double, Double> {
    override fun validate(value: Double): Result<Double> =
        value.takeIf { it >= 0 }
            ?.let { it.takeIf { it <= MAX_SCORE } ?: return Result.failure(ScoreValidationException(EXCEED_SCORE_MSG)) }
            ?.let { Result.success(it) }
            ?: Result.failure(ScoreValidationException(NEGATIVE_SCORE_MSG))

    companion object {
        const val NEGATIVE_SCORE_MSG = "Score cannot be negative"
        const val EXCEED_SCORE_MSG = "Score cannot exceed 100"
        private const val MAX_SCORE = 100.0
    }
}