package agalfioni.recipesai.home.di

import agalfioni.recipesai.home.presentation.home.HomeViewModel
import agalfioni.recipesai.recipe.data.repository.TastyRepositoryImpl
import agalfioni.recipesai.recipe.domain.interfaces.TastyRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {

    single<TastyRepository> { TastyRepositoryImpl(get()) }

    viewModelOf(::HomeViewModel)
}