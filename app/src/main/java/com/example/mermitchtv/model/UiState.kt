
package com.example.mermitchtv.model
import com.example.mermitchtv.model.ShowResult

sealed class UiState {
    object Loading : UiState()
    data class Success(val shows: List<ShowResult>) : UiState()
    data class Error(val message: String) : UiState()
}
