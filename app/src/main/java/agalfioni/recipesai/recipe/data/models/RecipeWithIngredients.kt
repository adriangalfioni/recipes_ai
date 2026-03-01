package agalfioni.recipesai.recipe.data.models

import agalfioni.recipesai.recipe.data.local.entity.IngredientEntity
import agalfioni.recipesai.recipe.data.local.entity.InstructionsEntity
import agalfioni.recipesai.recipe.data.local.entity.RecipeEntity
import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredients(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId",
    )
    val ingredients: List<IngredientEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId",
    )
    val instructions: List<InstructionsEntity>,
)
