package com.example.procoreinterview.data

data class PockemonApiResponse
    (
    val data: List<ApiPockemonCard>
)

data class ApiPockemonCard(
    val id: String,
    val name: String,
    val hp: String,
    val images: ApiPockemonCardImages,
    val superType: String,
    val subtypes: List<String>,
    val types: List<String>,

)

data class ApiPockemonCardImages(
    val small: String,
    val large: String
)
