package com.example.procoreinterview.data

data class PockemonApiResponse
    (
            val data: List<ApiPockemonCard>
            )

data class ApiPockemonCard(
    val id: String,
    val name : String,
    val hp: String,
    val images: ApiPockemonCardImages
)

data class ApiPockemonCardImages(
    val small: String
)