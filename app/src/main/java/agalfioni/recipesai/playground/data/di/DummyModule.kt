package agalfioni.recipesai.playground.data.di

import agalfioni.recipesai.playground.data.repository.DummyRepositoryImpl
import agalfioni.recipesai.playground.domain.DummyRepository
import agalfioni.recipesai.playground.presentation.DummyViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val dummyModule = module {

    single<DummyRepository> { DummyRepositoryImpl(api = get()) }

    viewModelOf(::DummyViewModel)

}