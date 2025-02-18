package com.example.lessonapp.api.interfaces

import com.example.lessonapp.models.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<MarvelResponse>

    @GET("characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: Long,
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response<MarvelResponse>
}
