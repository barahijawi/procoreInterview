package com.example.procoreinterview.domain

import com.example.procoreinterview.data.repository.PockemonRepository
import javax.inject.Inject

class GetPockemonCardUseCase @Inject constructor(
    private val respository : PockemonRepository

){
    suspend operator  fun invoke(page: Int = 1,size: Int = 50): List<PockemonCard> {

        return respository.getPockemonCards(page,size)
    }

    suspend fun getCardsCount():Int {
      return  respository.getCardCount()
    }
}
