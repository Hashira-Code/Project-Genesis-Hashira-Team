package domain.validation

import domain.exception.ScoreValidationException
class ScoreValidator : Validator<String, Double> {
    override fun validate(value: String): Result<Double> {
        val score = value.toDoubleOrNull()
            ?: return Result.failure(
                ScoreValidationException("Score must be a number"))
        if (score < 0) {
            return Result.failure(
                ScoreValidationException("Score cannot be negative"))
        }
        if (score > 100) {
            return Result.failure(
                ScoreValidationException("Score cannot exceed 100"))
        }
        return Result.success(score)
    }
}