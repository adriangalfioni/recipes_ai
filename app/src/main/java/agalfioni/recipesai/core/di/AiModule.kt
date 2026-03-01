package agalfioni.recipesai.core.di

import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import org.koin.dsl.module

val aiModule =
    module {
        single { provideGenerativeModel() }
    }

private fun provideGenerativeModel(): GenerativeModel =
    Firebase
        .ai(backend = GenerativeBackend.googleAI())
        .generativeModel(modelName = "gemini-2.5-flash")
