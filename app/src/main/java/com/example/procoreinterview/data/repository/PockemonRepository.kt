package com.example.procoreinterview.data.repository

import com.example.procoreinterview.domain.PockemonCard

interface  PockemonRepository{
    suspend fun getPockemonCards():List<PockemonCard>
}