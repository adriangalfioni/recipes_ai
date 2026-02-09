package agalfioni.recipesai.home.data.utils

import agalfioni.recipesai.home.data.FakeLoggingImageObserver
import agalfioni.recipesai.shared.image_proccessing.domain.ImageProcessor
import agalfioni.recipesai.shared.image_proccessing.data.ImageProcessorImpl
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.util.Random

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class ImageProcessorImplTest {

    lateinit var imageProcessor: ImageProcessor

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        imageProcessor = ImageProcessorImpl(context, FakeLoggingImageObserver())
    }

    @Test
    fun `compress large image reduces size`() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val uriString = createHugeTestImage(context)

        val result = imageProcessor.compressImageForAi(uriString)

        println("Original file size = ${getFileSize(uriString).toMBString()}")
        println("Compressed file size = ${result.size.toLong().toMBString()}")


        val thirtyPercentCompressionSize = (getFileSize(uriString) * 0.3).toLong()
        assertTrue("Compressed size was ${result.size.toLong().toMBString()}", result.size < thirtyPercentCompressionSize)
        assertTrue(result.isNotEmpty())

        getFileFromUri(uriString).delete()
    }

    private fun createHugeTestImage(context: Context): String {
        val width = 2000
        val height = 2000
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        // Add "Noise" so the file size is actually large
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val random = Random()
        repeat((0..1000).count()) {
            paint.color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
            canvas.drawCircle(random.nextFloat() * width, random.nextFloat() * height, 100f, paint)
        }

        val file = File(context.cacheDir, "test_heavy_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        bitmap.recycle()
        return Uri.fromFile(file).toString()
    }

    private fun getFileSize(uriString: String): Long {
        return getFileFromUri(uriString).length()
    }

    private fun getFileFromUri(uriString: String): File {
        // Convert the URI string back to a file
        val uri = Uri.parse(uriString)
        return if (uri.scheme == "file") {
            File(uri.path!!)
        } else {
            // Fallback for content URIs if you use them later
            File(uriString.replace("file:", ""))
        }
    }
}

private fun Long.toMBString(): String {
    return "${String.format("%.2f", this / 1024.0)} MB"
}