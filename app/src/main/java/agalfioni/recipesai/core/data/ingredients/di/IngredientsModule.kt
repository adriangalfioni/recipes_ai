package agalfioni.recipesai.core.data.ingredients.di

import agalfioni.recipesai.core.data.ai.data_source.IngredientsDetectorDataSource
import agalfioni.recipesai.core.data.ingredients.repository.IngredientsRepositoryImpl
import agalfioni.recipesai.core.data.repository.LocalIngredientsDataSource
import agalfioni.recipesai.core.ingredients.domain.interfaces.IngredientsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val ingredientsModule = module {

    single<IngredientsRepository> { IngredientsRepositoryImpl(get()) }

    single { LocalIngredientsDataSource(androidContext().assets) }

}