package agalfioni.recipesai.core.presentation.utils

fun String.removeStressAccents(): String {
    val normalized = java.text.Normalizer.normalize(this, java.text.Normalizer.Form.NFD)
    // This regex removes marks ONLY if the base letter is NOT 'n' or 'N'
    return normalized.replace(Regex("(?<![nN])\\p{M}+"), "")
}
