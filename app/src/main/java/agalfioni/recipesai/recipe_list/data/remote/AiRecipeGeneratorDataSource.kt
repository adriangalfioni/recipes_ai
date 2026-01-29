package agalfioni.recipesai.recipe_list.data.remote

import com.google.firebase.ai.GenerativeModel

class AiRecipeGeneratorDataSource(
    private val model: GenerativeModel
) {
    suspend fun generateRecipes(prompt: String): String {
        val response = model.generateContent(prompt)
        return response.text ?: ""
    }
}