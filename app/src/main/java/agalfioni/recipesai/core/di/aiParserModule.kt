package agalfioni.recipesai.core.di

import agalfioni.recipesai.core.data.helpers.AiJsonParser
import agalfioni.recipesai.core.data.ingredients.parser.JsonIngredientParser
import agalfioni.recipesai.core.ingredients.domain.interfaces.IngredientsParser
import org.koin.core.qualifier.named
import org.koin.dsl.module

val aiParserModule = module {
    single {
        AiJsonParser(
            json = get(qualifier = named("AiJson"))
        )
    }

    single<IngredientsParser> { JsonIngredientParser() }
}