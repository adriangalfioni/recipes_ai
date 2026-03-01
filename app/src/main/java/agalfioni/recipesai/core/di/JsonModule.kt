package agalfioni.recipesai.core.di

import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val jsonModule =
    module {

        single<Json>(qualifier = named("AiJson")) {
            Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                isLenient = true
                explicitNulls = false
            }
        }

        // Optional: another Json config for local/db/api if needed later
        single<Json>(qualifier = named("DefaultJson")) {
            Json {
                ignoreUnknownKeys = true
            }
        }
    }
