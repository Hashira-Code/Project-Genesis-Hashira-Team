package domain.validation

import domain.model.exception.ValueOutOfRangeException

class ScoreValidator : Validator<Double, Double> {
    override fun validate(value: Double): Result<Double> {
        if (value < 0) {
            return Result.failure(ValueOutOfRangeException(NEGATIVE_SCORE_MSG))
        }
        if (value > MAX_SCORE) {
            return Result.failure(ValueOutOfRangeException(EXCEED_SCORE_MSG))
        }
        return Result.success(value)
    }

    companion object {
        private const val MAX_SCORE = 100.0
        const val NEGATIVE_SCORE_MSG = "Score cannot be negative"
        const val EXCEED_SCORE_MSG = "Score cannot exceed $MAX_SCORE"
    }
}