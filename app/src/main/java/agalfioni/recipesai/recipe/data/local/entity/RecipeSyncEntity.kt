package agalfioni.recipesai.recipe.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipes_sync",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index("recipeId")]
)
data class RecipeSyncEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val synced: Boolean = false,
    val recipeId: String
)