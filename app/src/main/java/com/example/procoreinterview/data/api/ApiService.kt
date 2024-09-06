package com.example.procoreinterview.data.api

import com.example.procoreinterview.data.model.ApiItem
import com.example.procoreinterview.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService
{
    @Headers("Accept: application/json")
    @GET("db.json")
    suspend fun getItems(): ApiResponse

    @Headers("Accept application/json")
    @POST("item.json")
    suspend fun getItemDetails(itemId:String): ApiItem
}