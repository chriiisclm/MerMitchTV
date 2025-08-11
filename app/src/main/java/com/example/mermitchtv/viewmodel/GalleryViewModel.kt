package com.example.mermitchtv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateListOf
import com.example.mermitchtv.data.ShowImage
import com.example.mermitchtv.api.RetrofitInstance
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {
    private val _images = mutableStateListOf<ShowImage>()
    val images: List<ShowImage> = _images

    private var currentShowId: Int? = null

    fun loadImages(showId: Int) {
        if (showId == currentShowId) return  // Already loaded for this showId

        currentShowId = showId
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getShowImages(showId)
                _images.clear()
                _images.addAll(result)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}