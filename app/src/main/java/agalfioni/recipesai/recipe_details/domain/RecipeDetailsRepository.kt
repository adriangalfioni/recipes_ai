package agalfioni.recipesai.recipe_details.domain

import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeDetailsRepository {

    fun getRecipeById(id: String): Flow<Recipe?>
}