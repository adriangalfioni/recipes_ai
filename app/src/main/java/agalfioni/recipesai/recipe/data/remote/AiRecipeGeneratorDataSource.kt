package agalfioni.recipesai.recipe.data.remote

import com.google.firebase.ai.GenerativeModel

class AiRecipeGeneratorDataSource(
    private val model: GenerativeModel
) {
    suspend fun generateRecipes(prompt: String): String {
        val response = model.generateContent(prompt)
        return response.text ?: ""
    }
}