package agalfioni.recipesai.recipe.data.local.database

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Migration1To2Test {
    private val migrationTestDbName = "migration-test"

    @get:Rule
    val helper =
        MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            AppDatabase::class.java,
        )

    @Test
    fun migrate1To2_correctlyAddsColumnsAndTables() {
        // Create DB with version 1 schema
        helper.createDatabase(migrationTestDbName, 1).apply {
            // Insert sample row (v1 schema has no createdAt)
            execSQL(
                """
                INSERT INTO recipes (
                    id, title, difficulty, minutesTime, ingredientCoverage, calories
                ) VALUES (
                    'r1', 'Pizza', 0, 30, 0.8, 500.0
                )
                """.trimIndent(),
            )

            close()
        }

        // Reopen DB with migration
        val db =
            Room
                .databaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    AppDatabase::class.java,
                    migrationTestDbName,
                ).addMigrations(MIGRATION_1_2)
                .build()

        db.openHelper.writableDatabase.apply {
            // Verify createdAt column exists
            val cursor = query("PRAGMA table_info(recipes)")
            var foundCreatedAt = false
            while (cursor.moveToNext()) {
                val columnName = cursor.getString(1)
                if (columnName == "createdAt") {
                    foundCreatedAt = true
                }
            }
            cursor.close()

            assertTrue(foundCreatedAt)

            // Verify default applied to old row
            val createdAtCursor = query("SELECT createdAt FROM recipes WHERE id = 'r1'")
            createdAtCursor.moveToFirst()
            val createdAtValue = createdAtCursor.getLong(0)
            createdAtCursor.close()

            assertEquals(0L, createdAtValue)

            // Verify recipes_sync table exists
            val tableCursor =
                query(
                    """
                    SELECT name FROM sqlite_master 
                    WHERE type='table' AND name='recipes_sync'
                    """.trimIndent(),
                )

            assertTrue(tableCursor.count == 1)
            tableCursor.close()

            // Verify foreign key exists
            val fkCursor = query("PRAGMA foreign_key_list(recipes_sync)")
            assertTrue(fkCursor.count == 1)
            fkCursor.close()

            close()
        }

        db.close()
    }
}
