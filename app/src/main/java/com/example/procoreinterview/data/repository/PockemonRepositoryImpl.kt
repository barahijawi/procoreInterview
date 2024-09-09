package com.example.procoreinterview.data.repository

import com.example.procoreinterview.data.api.PockemonApiService
import com.example.procoreinterview.domain.PockemonCard
import javax.inject.Inject

class PockemonRepositoryImpl @Inject constructor(
    private val apiService : PockemonApiService
): PockemonRepository
{
    override suspend fun getPockemonCards(): List<PockemonCard>
    {
        return  apiService.getPockemonCards().data.map{
            apiCard ->PockemonCard(
            id = apiCard.id,
            name = apiCard.name,
            hp = apiCard.hp,
            imageUrl = apiCard.images.small
            )
        }
    }
}