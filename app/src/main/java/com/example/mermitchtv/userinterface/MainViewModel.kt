package com.example.mermitchtv.userinterface



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mermitchtv.model.ShowResult
import com.example.mermitchtv.repository.ShowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val shows: List<ShowResult>) : UiState()
    data class Error(val message: String) : UiState()
}

class MainViewModel : ViewModel() {
    private val repository = ShowRepository()

    private val _state = MutableStateFlow<UiState>(UiState.Idle)
    val state: StateFlow<UiState> = _state

    fun search(query: String) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val results = repository.searchShows(query)
                _state.value = UiState.Success(results)
            } catch (e: Exception) {
                _state.value = UiState.Error("Failed: ${e.message}")
            }
        }
    }
}
