package com.example.procoreinterview.data.repository

import com.example.procoreinterview.data.database.MinMaxHp
import com.example.procoreinterview.domain.PockemonCard
import kotlinx.coroutines.flow.Flow

interface  PockemonRepository{
    suspend fun getPockemonCards():List<PockemonCard>
    fun getCachedPokemonCards(): Flow<List<PockemonCard>>
    suspend fun savePokemonCards(cards: List<PockemonCard>)
    suspend fun getMinMaxHP(): MinMaxHp
}