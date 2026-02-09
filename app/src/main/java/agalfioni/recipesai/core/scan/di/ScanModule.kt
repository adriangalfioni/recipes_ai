package agalfioni.recipesai.core.scan.di

import agalfioni.recipesai.core.scan.domain.use_case.DetectIngredientsUseCase
import org.koin.dsl.module

val scanModule = module {

    factory { DetectIngredientsUseCase(get(), get(), get(), get()) }

}