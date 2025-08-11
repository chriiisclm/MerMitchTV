package com.example.mermitchtv.data

data class ShowImage(
    val resolutions: Resolutions?
)

data class Resolutions(
    val original: ImageUrl?,
    val medium: ImageUrl?
)

data class ImageUrl(
    val url: String?
)
