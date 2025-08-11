package com.example.mermitchtv.userinterface


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mermitchtv.model.ShowResult
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable

@Composable
fun MainScreen(viewModel: MainViewModel, onShowClick: (ShowResult) -> Unit) {
    val state by viewModel.state.collectAsState()
    var query by remember { mutableStateOf(TextFieldValue("")) }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Shows") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { viewModel.search(query.text) }, Modifier.padding(top = 8.dp)) {
            Text("Search")
        }

        Spacer(Modifier.height(16.dp))

        when (val s = state) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Success -> ShowList(s.shows, onShowClick)
            is UiState.Error -> Text("Error: ${s.message}", color = MaterialTheme.colorScheme.error)
            UiState.Idle -> Text("Enter a show name to search.")
        }
    }
}

@Composable
fun ShowList(shows: List<ShowResult>, onShowClick: (ShowResult) -> Unit) {
    LazyColumn {
        items(shows) { result ->
            val show = result.show
            Column(
                Modifier
                    .padding(8.dp)
                    .clickable { onShowClick(result) } // Click to navigate
            ) {
                Text(show.name, style = MaterialTheme.typography.titleMedium)
                show.image?.medium?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(vertical = 8.dp)
                    )
                }
                Text("Language: ${show.language ?: "N/A"}")
                Text("Genres: ${show.genres.joinToString()}")
            }
        }
    }
}
