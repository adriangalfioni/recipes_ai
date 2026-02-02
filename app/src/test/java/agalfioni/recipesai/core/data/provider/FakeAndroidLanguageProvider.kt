package agalfioni.recipesai.core.data.provider

import agalfioni.recipesai.core.domain.interfaces.LanguageProvider

class FakeAndroidLanguageProvider: LanguageProvider {

    var languageSelected = Language.ENGLISH

    override fun getLanguage(): String {
        when (languageSelected) {
            Language.ENGLISH -> return "English"
            Language.SPANISH -> return "Spanish"
        }
    }

}

enum class Language {
    ENGLISH, SPANISH
}