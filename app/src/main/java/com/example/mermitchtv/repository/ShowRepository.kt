package com.example.mermitchtv.repository



import com.example.mermitchtv.api.RetrofitInstance
import com.example.mermitchtv.model.ShowResult

class ShowRepository {
    suspend fun searchShows(query: String): List<ShowResult> {
        return RetrofitInstance.api.searchShows(query)
    }
}
