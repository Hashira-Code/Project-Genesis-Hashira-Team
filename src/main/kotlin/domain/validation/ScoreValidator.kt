package domain.validation
import domain.exception.ScoreValidationException
class ScoreValidator: Validator<Int> {
    override fun validate(value: Int): Result<Int> {
        if (value < 0) {
            return Result.failure(
                ScoreValidationException("Score cannot be negative"))
        }
        if (value > 100) {
            return Result.failure(
                ScoreValidationException("Score cannot exceed 100"))
        }
        return Result.success(value)
    }
}