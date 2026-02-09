package agalfioni.recipesai.ingredients_detector.presentation.components

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.theme.GreenDot
import agalfioni.recipesai.core.presentation.theme.OrangeDot
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage

@Composable
fun ScannedImage(
    imageUri: Uri,
    scanCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(RoundedCornerShape(28.dp)),
    ) {
        ImageFromUri(
            imageUri = imageUri
        )
        GradientOverlay(
            modifier = Modifier.matchParentSize()
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 14.dp, bottom = 12.dp)
                .wrapContentWidth()
                .background(color = Color.Black, shape = CircleShape)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DrawFilledCircle(
                circleColor = if (scanCompleted) GreenDot else OrangeDot
            )
            Text(
                text = if (scanCompleted) {
                    stringResource(R.string.ai_scan_completed).uppercase()
                } else {
                    stringResource(R.string.ai_scan_in_progress).uppercase()
                },
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Preview
@Composable
private fun ScannedImagePreview() {
    RecipesAITheme {
        ScannedImage(
            imageUri = Uri.EMPTY,
            scanCompleted = true
        )
    }
}

@Composable
fun GradientOverlay(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.15f), // light top
                        Color.Black   // dark bottom
                    )
                )
            )
    )
}

@Composable
fun ImageFromUri(
    imageUri: Uri?,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = imageUri,
        contentDescription = "Fridge Photo",
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop, // Ensures the fridge photo fills the Box
        loading = {
            // This is shown while the image is loading
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
        },
    )
}

@Composable
fun DrawFilledCircle(
    circleColor: Color,
    circleSize: Dp = 12.dp
) {
    Canvas(modifier = Modifier.size(circleSize)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // The drawCircle function draws a filled circle by default
        drawCircle(
            color = circleColor, // Specify the fill color
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2), // Center of the circle
            radius = size.minDimension / 2 // Radius of the circle
        )
    }
}