package agalfioni.recipesai.home.data.utils

import agalfioni.recipesai.home.domain.interfaces.ImageProcessingObserver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.graphics.scale
import java.io.ByteArrayOutputStream

class ImageProcessor(
    private val context: Context,
    private val imageProcessingObserver: ImageProcessingObserver
) {

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


    fun compressImageForAi(
        uri: Uri, maxSize: Int = 1024, quality: Int = 70
    ): ByteArray {

        val resolver = context.contentResolver

        // Decode bounds only (no memory allocation)
        val boundsOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        // Calculate sample size
        val decodeOptions = BitmapFactory.Options().apply {
            inSampleSize = calculateInSampleSize(boundsOptions, maxSize)
            inPreferredConfig = Bitmap.Config.RGB_565 // AI doesn’t need alpha channel, less memory than ARGB_8888
            inJustDecodeBounds = false
        }

        // Decode scaled bitmap
        val bitmap = resolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, decodeOptions)
        }  ?: throw IllegalArgumentException("Cannot open URI")

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

        Log.d("asd", "compressImageForAi: asd asd")
        imageProcessingObserver.onCompressionCompleted(
            originalSizeBytes = getFileSize(uri),
            compressedSizeBytes = outputStream.size().toLong()
        )

        return outputStream.toByteArray()
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