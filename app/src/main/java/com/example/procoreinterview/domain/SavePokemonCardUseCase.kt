package com.example.procoreinterview.domain

import com.example.procoreinterview.data.repository.PockemonRepository
import javax.inject.Inject

class SavePokemonCardUseCase @Inject constructor(
    private val repository: PockemonRepository
)
{
    suspend operator fun invoke(cards: List<PockemonCard>){
        repository.savePokemonCards(cards)
    }
}