package com.example.procoreinterview.domain

import com.example.procoreinterview.data.repository.PockemonRepository
import javax.inject.Inject

class GetPockemonCardUseCase @Inject constructor(
    private val respository : PockemonRepository
){
    suspend operator  fun invoke(): List<PockemonCard> {
        return respository.getPockemonCards()
    }
}
