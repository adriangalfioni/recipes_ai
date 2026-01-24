package agalfioni.recipesai.core.di

import agalfioni.recipesai.home.presentation.HomeViewModel
import agalfioni.recipesai.scan_result.presentation.IngredientsDetectorViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // 1. Singletons (like @Singleton in Hilt)
    // Creates ONE instance for the whole app
    //single<PetApi> { RetrofitClient.api }
    //single<PetRepository> { PetRepositoryImpl(get()) } // 'get()' auto-resolves dependencies

    // 2. ViewModels (like @HiltViewModel)
    // Creates a new instance every time the screen needs it
    //viewModel { IngredientsDetectorViewModel(ingredientsDetectorRepository = get()) }
    viewModelOf(::IngredientsDetectorViewModel)
    viewModelOf(::HomeViewModel)
}