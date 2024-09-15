package com.example.procoreinterview.domain

import com.example.procoreinterview.data.database.MinMaxHp
import com.example.procoreinterview.data.repository.PockemonRepository
import javax.inject.Inject

class GetMinMaxHpUseCase@Inject constructor(
    private val repository: PockemonRepository
) {
    suspend operator fun invoke(): MinMaxHp
    {
        return repository.getMinMaxHP()
    }
}