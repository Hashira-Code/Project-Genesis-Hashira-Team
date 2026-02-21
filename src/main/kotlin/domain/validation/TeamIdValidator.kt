package domain.validation
import domain.exception.IdValidationException
class TeamIdValidator: Validator<String,String> {
    override fun validate(value: String): Result<String> {
        if (value.isBlank()) {
            return Result.failure(
                IdValidationException("Team ID cannot be empty"))
        }
        val regex = Regex("^[a-z-]+$")
        if (!regex.matches(value)) {
            return Result.failure(
                IdValidationException(
                    "Team ID must contain only lowercase letters and hyphens"))
        }
        return Result.success(value)
    }
}