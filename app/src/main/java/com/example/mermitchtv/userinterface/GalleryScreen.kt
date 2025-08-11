package com.example.mermitchtv.userinterface
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import com.example.mermitchtv.viewmodel.GalleryViewModel

@Composable
fun GalleryScreen(viewModel: GalleryViewModel, showId: Int) {
    var isLoading by remember { mutableStateOf(true) }
    val images = viewModel.images

    // State to hold currently selected image URL for fullscreen
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    // Load images when screen starts
    LaunchedEffect(showId) {
        viewModel.loadImages(showId)
        isLoading = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                // Show loading spinner
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            images.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No images found.", style = MaterialTheme.typography.bodyLarge)
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 120.dp),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(images) { image ->
                        val url = image.resolutions?.medium?.url ?: image.resolutions?.original?.url
                        if (url != null) {
                            Image(
                                painter = rememberAsyncImagePainter(url),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                                    .clickable {
                                        selectedImageUrl = url // set clicked image URL
                                    }
                            )
                        }
                    }
                }
            }
        }

        // Fullscreen image overlay/dialog
        if (selectedImageUrl != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.9f))
                    .clickable { selectedImageUrl = null }, // dismiss on tap outside
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUrl!!),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                )
            }
        }
    }
}
