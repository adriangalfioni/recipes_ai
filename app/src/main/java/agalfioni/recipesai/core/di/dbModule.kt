package agalfioni.recipesai.core.di

import agalfioni.recipesai.core.data.recipes.local.AppDatabase
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "recipes-ai-db").build() }

    single { get<AppDatabase>().recipeDao() }
}