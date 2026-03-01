package agalfioni.recipesai.core.di

import agalfioni.recipesai.core.data.helpers.AiJsonParser
import org.koin.core.qualifier.named
import org.koin.dsl.module

val aiParserModule =
    module {
        single {
            AiJsonParser(
                json = get(qualifier = named("AiJson")),
            )
        }
    }
