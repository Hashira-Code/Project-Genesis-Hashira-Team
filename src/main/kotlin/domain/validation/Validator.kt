package domain.validation

interface Validator<T> {
    fun validate(value : T):  Result<T>
}