package agalfioni.recipesai.core.di

import agalfioni.recipesai.recipe.data.local.database.AppDatabase
import agalfioni.recipesai.recipe.data.local.database.MIGRATION_1_2
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "recipes-ai-db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    single { get<AppDatabase>().recipeDao() }

    single { get<AppDatabase>().recipesSyncDao() }
}