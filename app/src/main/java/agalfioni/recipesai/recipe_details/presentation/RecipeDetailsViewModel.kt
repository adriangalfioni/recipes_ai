package agalfioni.recipesai.recipe_details.presentation


import agalfioni.recipesai.recipe_details.presentation.mappers.toRecipeDetailsUi
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecipeDetailsViewModel(
    private val recipe: Recipe
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailsState())
    val uiState: StateFlow<RecipeDetailsState> = _uiState.asStateFlow()


    init {
        _uiState.update {
            it.copy(recipeDetailsUi = recipe.toRecipeDetailsUi())
        }
    }

}