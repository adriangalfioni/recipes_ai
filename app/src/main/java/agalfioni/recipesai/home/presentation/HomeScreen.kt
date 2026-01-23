package agalfioni.recipesai.home.presentation

import agalfioni.recipesai.core.presentation.components.RecipeButton
import agalfioni.recipesai.core.presentation.components.RecipeOutlinedButton
import agalfioni.recipesai.core.presentation.components.RecipeSelectableButton
import agalfioni.recipesai.core.presentation.components.RecipeSelectableOutlinedButton
import agalfioni.recipesai.core.presentation.components.rememberCameraLauncher
import agalfioni.recipesai.scan_result.presentation.IngredientsDetectorEvent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onImage: (String) -> Unit,
    //viewModel: HomeViewModel = koinViewModel()
) {
    var secondSelected by remember { mutableStateOf(false) }
    var secondSelected2 by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        var detectedIngredients by remember { mutableStateOf("No ingredients detected yet.") }

        // 1. Setup the Gallery Launcher
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                imageUri = uri
                onImage(imageUri.toString())
            }
        }

        val takePhoto = rememberCameraLauncher(
            onImageCaptured = { uri ->
                imageUri = uri
                onImage(imageUri.toString())
            }
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 2. Button to Pick Image
            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Fridge Photo")
            }
            Button(
                onClick = { takePhoto() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Take Fridge Photo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Display Selected Image
            imageUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Display Results
            Text(
                text = "Detected Items:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = detectedIngredients,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Text(text = "Hello Koin!")
        Spacer(modifier = Modifier.height(12.dp))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RecipeButton("15 mins (easy)", {})
            RecipeSelectableButton("30 mins (moderate)", {secondSelected = !secondSelected}, secondSelected)
            RecipeSelectableButton(">45 mins (elaborated)", {}, false)
        }

        Spacer(modifier = Modifier.height(12.dp))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RecipeOutlinedButton("15 mins (easy)", {})
            RecipeSelectableOutlinedButton("30 mins (moderate)", {
                secondSelected2 = !secondSelected2
            }, secondSelected2)
            RecipeSelectableOutlinedButton(">45 mins (elaborated)", {}, false)
        }
    }
}