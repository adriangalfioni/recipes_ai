package agalfioni.recipesai.recipe.data.local.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("""
            ALTER TABLE recipes
            ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS recipes_sync (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                synced INTEGER NOT NULL,
                recipeId TEXT NOT NULL,
                FOREIGN KEY(recipeId) REFERENCES recipes(id)
                ON DELETE CASCADE
            )
        """.trimIndent())

        db.execSQL("""
            CREATE INDEX IF NOT EXISTS index_recipes_sync_recipeId
            ON recipes_sync(recipeId)
        """.trimIndent())
    }
}
