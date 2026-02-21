package domain.validation

interface Validator<I,O> {
    fun validate(value : I):  Result<O>
}