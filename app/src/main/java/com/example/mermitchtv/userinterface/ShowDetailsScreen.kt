package com.example.mermitchtv.userinterface
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.layout.ContentScale
import com.example.mermitchtv.model.Show // Make sure this is the correct path to your Show model


@Composable
fun ShowDetailsScreen(
    show: Show,
    onViewGalleryClick: (Int) -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text(show.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        show.image?.original?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        Spacer(Modifier.height(8.dp))
        Text("Language: ${show.language ?: "N/A"}")
        Text("Genres: ${show.genres.joinToString()}")
        Text("Premiered: ${show.premiered ?: "N/A"}")
        Text("Rating: ${show.rating?.average ?: "N/A"}")

        Spacer(Modifier.height(8.dp))
        Text("Summary:", style = MaterialTheme.typography.titleMedium)
        Text(show.summary?.replace(Regex("<[^>]*>"), "") ?: "No summary available.")

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { onViewGalleryClick(show.id) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Gallery")
        }
    }
}