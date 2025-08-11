package com.example.mermitchtv.api

import com.example.mermitchtv.model.ShowResult
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path
import com.example.mermitchtv.data.ShowImage

interface TvMazeApi {
    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): List<ShowResult>

    @GET("shows/{id}/images")
    suspend fun getShowImages(@Path("id") showId: Int): List<ShowImage>
}

object RetrofitInstance {
    val api: TvMazeApi by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(TvMazeApi::class.java)
    }
}
