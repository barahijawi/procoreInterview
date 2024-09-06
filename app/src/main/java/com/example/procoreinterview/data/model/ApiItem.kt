package com.example.procoreinterview.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val items: List<ApiItem>  // This corresponds to the "items" key in the JSON
)

data class ApiItem(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name : String,
    @SerializedName("description") val description : String,
    @SerializedName("image") val image:String
)
