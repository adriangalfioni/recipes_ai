package agalfioni.recipesai.core.data.ai.data_source

import com.google.firebase.ai.GenerativeModel

class AiRecipeGeneratorDataSource(
    private val model: GenerativeModel
) {
    suspend fun generateRecipes(prompt: String): String {
        val response = model.generateContent(prompt)
        return response.text ?: ""
    }
}