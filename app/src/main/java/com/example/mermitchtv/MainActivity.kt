package com.example.mermitchtv
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mermitchtv.userinterface.ShowDetailsScreen
import androidx.compose.material3.Text
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mermitchtv.userinterface.MainScreen
import com.example.mermitchtv.userinterface.MainViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.activity.viewModels
import android.net.Uri
import kotlinx.serialization.Serializable
import com.example.mermitchtv.model.ShowResult
import com.example.mermitchtv.model.Show
import com.example.mermitchtv.model.ShowImage
import com.example.mermitchtv.userinterface.GalleryScreen
import com.example.mermitchtv.viewmodel.GalleryViewModel


class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MermitchTVApp(viewModel = mainViewModel)
        }
    }
}

@Composable
fun MermitchTVApp(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "search") {

        // Search screen
        composable("search") {
            MainScreen(
                viewModel = viewModel,
                onShowClick = { showResult ->
                    val json = Uri.encode(Json.encodeToString(showResult))
                    navController.navigate("details/$json")
                }
            )
        }

        // Details screen
        composable(
            route = "details/{showJson}",
            arguments = listOf(navArgument("showJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("showJson")
            val showResult = json?.let { Json.decodeFromString<ShowResult>(it) }

            if (showResult != null) {
                ShowDetailsScreen(
                    show = showResult.show,
                    onViewGalleryClick = { showId ->
                        if (showId > 0) {
                            navController.navigate("gallery/$showId")
                        }
                    }
                )
            } else {
                Text("Show not found.")
            }
        }

        // Gallery screen
        composable(
            route = "gallery/{showId}",
            arguments = listOf(navArgument("showId") { type = NavType.IntType })
        ) { backStackEntry ->
            val showId = backStackEntry.arguments?.getInt("showId") ?: 0
            if (showId > 0) {
                val galleryViewModel: GalleryViewModel = viewModel()
                GalleryScreen(viewModel = galleryViewModel, showId = showId)
            } else {
                Text("Invalid Show ID")
            }

        }
// Simple error screen route

        composable(
            route = "error/{message}",
            arguments = listOf(navArgument("message") { type = NavType.StringType })
        ) { backStackEntry ->
            val message = backStackEntry.arguments?.getString("message") ?: "Unknown error"
            Column(Modifier.padding(16.dp)) {
                Text("Error: $message", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}





@Serializable
data class ShowResult(
    val score: Double,
    val show: Show
)

@Serializable
data class Show(
    val id: Int,
    val name: String,
    val language: String?,
    val genres: List<String>,
    val image: ShowImage?
)

@Serializable
data class ShowImage(
    val medium: String?,
    val original: String?
)


