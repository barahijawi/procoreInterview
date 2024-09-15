package com.example.procoreinterview.domain

import com.example.procoreinterview.data.repository.PockemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCachedPockemonCardUseCase @Inject constructor(
    private val repository: PockemonRepository
)
{
    operator fun invoke(): Flow<List<PockemonCard>>
    {
        return repository.getCachedPokemonCards()
    }
}