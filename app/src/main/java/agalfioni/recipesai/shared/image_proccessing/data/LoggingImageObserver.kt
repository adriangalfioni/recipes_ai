package agalfioni.recipesai.shared.image_proccessing.data

import agalfioni.recipesai.shared.image_proccessing.domain.ImageProcessingObserver
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

class LoggingImageObserver(
    private val firebaseAnalytics: FirebaseAnalytics
): ImageProcessingObserver {

    companion object {
        private const val GEMINI_IMAGE_ANALYSIS_KEY = "gemini_image_analysis"
        private const val ORIGINAL_FILE_SIZE_PARAM = "original_file_size_kb"
        private const val COMPRESSED_FILE_SIZE_PARAM = "compressed_file_size_kb"
        private const val TIMESTAMP_PARAM = "timestamp"
    }

    override fun onCompressionCompleted(
        originalSizeBytes: Long,
        compressedSizeBytes: Long
    ) {
        val originalSizeKb = originalSizeBytes / 1024
        val compressedSizeKb = compressedSizeBytes / 1024

        firebaseAnalytics.logEvent(GEMINI_IMAGE_ANALYSIS_KEY) {
            param(ORIGINAL_FILE_SIZE_PARAM, originalSizeKb)
            param(COMPRESSED_FILE_SIZE_PARAM, compressedSizeKb)
            param(TIMESTAMP_PARAM, System.currentTimeMillis())
        }


        Log.d(GEMINI_IMAGE_ANALYSIS_KEY, "Gemini Request | Original size: ${originalSizeKb}KB, compressed size ${compressedSizeKb}KB")
    }
}