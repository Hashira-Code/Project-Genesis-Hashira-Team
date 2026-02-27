package data.validation
import java.io.File
class FileValidator : Validator<File>{
    override fun validate(input: File): Result<File> {
        return runCatching { input }
            .mapCatching (::validateExists)
            .mapCatching(::validateIsFile)
            .mapCatching(::validateCsvExtension)
            .mapCatching(::validateNotEmpty)

    }
    private fun validateExists(file: File): File
      =  file.takeIf { it.exists() }
        ?: throw IllegalArgumenException("File does not exist: ${file.path}")

    private fun validateIsFile(file: File):File
    = file.takeIf { it.isFile }
        ?: throw IllegalArgumenException("Path is not a file: ${file.path}")

    private fun validateCsvExtension(file: File):File
    = file.takeIf { it.extension.equals("csv", ignoreCase=true) }
        ?: throw IllegalArgumenException("File must be CSV ${file.path}")

    private fun validateNotEmpty(file: File):File
    = file.takeIf { it.length() > 0 }
        ?: throw IllegalArgumenException("File is Empty: ${file.path}")





}