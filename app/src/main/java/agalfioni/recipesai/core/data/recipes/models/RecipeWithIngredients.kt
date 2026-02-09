package agalfioni.recipesai.core.data.recipes.models

import agalfioni.recipesai.core.data.recipes.local.entity.IngredientEntity
import agalfioni.recipesai.core.data.recipes.local.entity.InstructionsEntity
import agalfioni.recipesai.core.data.recipes.local.entity.RecipeEntity
import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredients(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val ingredients: List<IngredientEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val instructions: List<InstructionsEntity>
)
