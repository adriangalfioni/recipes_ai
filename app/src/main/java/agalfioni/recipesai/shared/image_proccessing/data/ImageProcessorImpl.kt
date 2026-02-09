package agalfioni.recipesai.shared.image_proccessing.data

import agalfioni.recipesai.shared.image_proccessing.domain.ImageProcessingObserver
import agalfioni.recipesai.shared.image_proccessing.domain.ImageProcessor
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import androidx.core.graphics.scale
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class ImageProcessorImpl(
    private val context: Context,
    private val imageProcessingObserver: ImageProcessingObserver
): ImageProcessor {

    override suspend fun compressImageForAi(
        uriString: String, maxSize: Int, quality: Int
    ): ByteArray = withContext(Dispatchers.IO) {
        val uri = uriString.toUri()
        val resolver = context.contentResolver

        // Calculate sample size
        val decodeOptions = BitmapFactory.Options().apply {
            inSampleSize = calculateInSampleSize(BitmapFactory.Options(), maxSize)
            inPreferredConfig =
                Bitmap.Config.RGB_565 // AI doesn’t need alpha channel, less memory than ARGB_8888
            inJustDecodeBounds = false
        }

        // Decode scaled bitmap
        val bitmap = resolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, decodeOptions)
        } ?: throw IllegalArgumentException("Cannot open URI")

        // Resize to exact target if needed
        val ratio = minOf(
            maxSize.toFloat() / bitmap.width, maxSize.toFloat() / bitmap.height, 1f
        )

        val resizedBitmap =
            bitmap.scale((bitmap.width * ratio).toInt(), (bitmap.height * ratio).toInt())

        // Compress
        val outputStream = ByteArrayOutputStream()

        resizedBitmap.compress(
            getBestCompressFormat(), quality, outputStream
        )

        imageProcessingObserver.onCompressionCompleted(
            originalSizeBytes = getFileSize(uri),
            compressedSizeBytes = outputStream.size().toLong()
        )

        return@withContext outputStream.toByteArray()
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options, maxSize: Int
    ): Int {

        val (height, width) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > maxSize || width > maxSize) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while (halfHeight / inSampleSize >= maxSize && halfWidth / inSampleSize >= maxSize) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun getBestCompressFormat(): Bitmap.CompressFormat {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Bitmap.CompressFormat.WEBP_LOSSY
        } else {
            Bitmap.CompressFormat.WEBP
        }
    }

    private fun getFileSize(uri: Uri): Long {

        val cursor = context.contentResolver.query(
            uri,
            arrayOf(OpenableColumns.SIZE),
            null,
            null,
            null
        )

        cursor?.use {
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            if (it.moveToFirst() && sizeIndex != -1) {
                return it.getLong(sizeIndex)
            }
        }

        return 0L
    }

}