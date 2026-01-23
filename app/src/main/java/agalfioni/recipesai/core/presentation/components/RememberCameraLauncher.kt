package agalfioni.recipesai.core.presentation.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

/**
 * A reusable hook for taking pictures.
 * @param onImageCaptured Callback triggered when a photo is successfully taken.
 * @return A lambda function that you call to open the camera.
 */
@Composable
fun rememberCameraLauncher(
    onImageCaptured: (Uri) -> Unit,
    onError: () -> Unit = {}
): () -> Unit {
    val context = LocalContext.current

    // Hold the URI internally so we remember where we asked the camera to save
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempUri != null) {
            onImageCaptured(tempUri!!)
        } else {
            onError()
        }
    }

    // Return a simple function that creates the file and launches the camera
    return remember(context, launcher) {
        {
            val uri = createTempPictureUri(context)
            tempUri = uri
            launcher.launch(uri)
        }
    }
}

// Helper function
private fun createTempPictureUri(context: Context): Uri {
    val tempFile = File.createTempFile("img_${System.currentTimeMillis()}", ".jpg", context.cacheDir).apply {
        createNewFile()
    }
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", tempFile)
}
