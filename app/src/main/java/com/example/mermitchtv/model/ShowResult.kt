//package com.example.mermitchtv.model
package com.example.mermitchtv.model
import kotlinx.serialization.Serializable
@Serializable

data class ShowResult(
    val show: Show
)
@Serializable

data class Show(
    val id: Int,
    val name: String,
    val language: String?,
    val genres: List<String>,
    val image: ShowImage?,
    val rating: ShowRating?,
    val premiered: String?,
    val summary: String?
)
@Serializable
data class ShowImage(
    val medium: String?,
    val original: String?
)
@Serializable
data class ShowRating(
    val average: Double?
)
