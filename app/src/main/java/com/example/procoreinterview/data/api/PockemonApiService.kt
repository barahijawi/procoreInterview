package com.example.procoreinterview.data.api

import com.example.procoreinterview.data.PockemonApiResponse
import retrofit2.http.GET

interface PockemonApiService {
    @GET("cards")
    suspend fun getPockemonCards(): PockemonApiResponse

}