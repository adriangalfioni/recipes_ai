package agalfioni.recipesai.recipe_list.data

import com.google.firebase.ai.GenerativeModel

class RecipeGeneratorDataSource(
    private val model: GenerativeModel
) {
    suspend fun generateRecipes(prompt: String): String {
        val response = model.generateContent(prompt)
        return response.text ?: ""
    }
}