package agalfioni.recipesai.core.data.provider

import agalfioni.recipesai.core.domain.interfaces.LanguageProvider
import android.content.Context

class AndroidLanguageProvider(
    private val context: Context,
) : LanguageProvider {
    override fun getLanguage(): String {
        // This respects both System settings and In-App language overrides
        val code =
            context.resources.configuration.locales[0]
                .language
        return when {
            code.startsWith("es") -> "Spanish"
            else -> "English" // Default to English for everything else
        }
    }
}
