package data.validation

interface Validator<T> {
    fun validate(input : T):  Result<T>
}