package domain.validation

import domain.model.exception.ValidationExeption.ValueOutOfRangeExeption

class ScoreValidator : Validator<Double, Double> {
    override fun validate(value: Double): Result<Double> {
        if (value < 0) {
            return Result.failure(ValueOutOfRangeExeption(NEGATIVE_SCORE_MSG))
        }
        if (value > MAX_SCORE) {
            return Result.failure(ValueOutOfRangeExeption(EXCEED_SCORE_MSG))
        }
        return Result.success(value)
    }

    companion object {
        private const val MAX_SCORE = 100.0
        const val NEGATIVE_SCORE_MSG = "Score cannot be negative"
        const val EXCEED_SCORE_MSG = "Score cannot exceed $MAX_SCORE"
    }
}