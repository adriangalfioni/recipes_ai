package agalfioni.recipesai.core.di

import agalfioni.recipesai.core.data.provider.AndroidLanguageProvider
import agalfioni.recipesai.core.domain.interfaces.LanguageProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidLanguageModule =
    module {
        single<LanguageProvider> { AndroidLanguageProvider(androidContext()) }
    }
