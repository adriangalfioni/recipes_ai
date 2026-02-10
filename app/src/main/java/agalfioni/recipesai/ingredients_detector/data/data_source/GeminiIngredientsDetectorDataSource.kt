package agalfioni.recipesai.ingredients_detector.data.data_source

import android.graphics.Bitmap
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.type.content

class GeminiIngredientsDetectorDataSource(
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

    suspend fun generateContent(compressedBytes: ByteArray, prompt: String): String {
        val response = model.generateContent(
            content {
                inlineData(
                    bytes = compressedBytes,
                    mimeType = "image/webp"
                )
                text(prompt)
            }
        )
        return response.text ?: ""
    }
}