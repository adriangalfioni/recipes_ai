package agalfioni.recipesai.home.presentation.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun AttentionSeekerTranslationAlpha(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    if (!enabled) {
        content()
        return
    }

    val infiniteTransition = rememberInfiniteTransition(label = "attention")

    // 1. Subtle Floating Animation (Vertical translation)
    val translateY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -6f, // Moves up 6dp
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "translationY"
    )

    // 2. Subtle Alpha Pulse (Glow effect)
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                //translationY = translateY
                this.alpha = alpha
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun AttentionSeekerScale(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    if (!enabled) {
        content()
        return
    }

    val infiniteTransition = rememberInfiniteTransition(label = "attention_seeker")

    // 1. Scale Animation: Increases size by 8% and returns to original (1.0f)
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // 2. Vertical Float: Moves up 4dp to complement the scaling
    val translateY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "translationY"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationY = translateY
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}