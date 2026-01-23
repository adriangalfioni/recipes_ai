package agalfioni.recipesai.scan_result.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.graphics.scale

class ImageProcessor(private val context: Context) {

    fun prepareBitmapForAnalysis(uri: Uri): Bitmap {
        // 1. Open the stream and decode the full-size image
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Cannot open URI")

        val fullSizeBitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        // 2. Calculate scale to fit within 1024px while maintaining aspect ratio
        val maxSide = 1024
        val scale = minOf(
            maxSide.toFloat() / fullSizeBitmap.width,
            maxSide.toFloat() / fullSizeBitmap.height
        )

        // 3. Create the resized bitmap
        val resizedBitmap = fullSizeBitmap.scale(
            (fullSizeBitmap.width * scale).toInt(),
            (fullSizeBitmap.height * scale).toInt()
        )

        // 4. Recycle the original heavy bitmap to free up memory immediately
        if (fullSizeBitmap != resizedBitmap) {
            fullSizeBitmap.recycle()
        }

        return resizedBitmap
    }
}