package agalfioni.recipesai.scan_result.data

import android.graphics.Bitmap
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.type.content

class IngredientsDetectorDataSource(
    private val model: GenerativeModel
) {
    suspend fun generateContent(bitmap: Bitmap, prompt: String): String {
        val response = model.generateContent(
            content {
                image(bitmap)
                text(prompt)
            }
        )
        return response.text ?: ""
    }
}