package com.example.procoreinterview.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procoreinterview.data.repository.PockemonRepository
import com.example.procoreinterview.domain.GetPockemonCardUseCase
import com.example.procoreinterview.domain.PockemonCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.ceil

sealed class PockemonUiState
{
    object Loading : PockemonUiState()
    data class Success(val data: List<PockemonCard>) : PockemonUiState()
    data class Error(val message: String) : PockemonUiState()
}


@HiltViewModel
class PockemonViewModel @Inject constructor(
    private val getPockemonCardUseCase: GetPockemonCardUseCase,
    private val repository: PockemonRepository
) : ViewModel()
{

    private val _pockemonUiState = MutableStateFlow<PockemonUiState>(PockemonUiState.Loading)
    val pockemonUiState: StateFlow<PockemonUiState> = _pockemonUiState

    private val _totalCount = MutableStateFlow<Int>(0)
    val totalCount :StateFlow<Int> = _totalCount

        private var currentPage = 1
    private val pageSize = 20



    init
    {
        fetchPockemonCards()
    }

//    private fun fetchPockemonCards() {
//        viewModelScope.launch {
//            try {
//                _pockemonUiState.value = PockemonUiState.Loading
//                val cards = getPockemonCardUseCase()
//                _pockemonUiState.value = PockemonUiState.Success(cards)
//            } catch (e: Exception) {
//                Log.e("PockemonViewModel", "Failed to load cards", e)
//                _pockemonUiState.value = PockemonUiState.Error("Failed to load cards")
//            }
//        }
//    }

    //NO DB
    fun sortByHp(cards: List<PockemonCard>): List<PockemonCard>
    {
        return cards.sortedBy { it.hp.toIntOrNull() ?: 0 }
    }

    // No DB Function to handle sorting by HP or Type
    fun sortBy(criteria: String) {
        val currentState = _pockemonUiState.value
        if (currentState is PockemonUiState.Success) {
            val sortedCards = when (criteria) {
                "hp" -> currentState.data.sortedBy { it.hp.toIntOrNull() ?: 0 } // Sort by HP
//                "type" -> currentState.data.sortedBy { it. } // Sort by Type
                else -> currentState.data
            }
            _pockemonUiState.value = PockemonUiState.Success(sortedCards)
        }
    }

    //DB SORT
    // Sort by HP
    fun sortByHp() {
        viewModelScope.launch {
            try {
                _pockemonUiState.value = PockemonUiState.Loading
                val sortedCards = repository.getPockemonCardsSortedByHp()
                _pockemonUiState.value = PockemonUiState.Success(sortedCards)
            } catch (e: Exception) {
                _pockemonUiState.value = PockemonUiState.Error("Failed to sort by HP")
            }
        }
    }

    fun fetchPockemonCards()
    {
        val currentState = _pockemonUiState.value
        Log.d("pageNumber", "fetchPockemonCards: $currentPage")
        if (currentState is PockemonUiState.Loading && currentPage != 1) return // Avoid multiple simultaneous loads

        viewModelScope.launch {
            if(_totalCount.value ==0)
            {
                withContext(Dispatchers.IO)
                {
                    _totalCount.value = getPockemonCardUseCase.getCardsCount()
                }
            }
//            _pockemonUiState.value = PockemonUiState.Loading
            val newCards = getPockemonCardUseCase(currentPage, pageSize) // Fetch paginated data

            Log.d("pageNumber", "newCards.size: ${newCards.size}")
            if (currentState is PockemonUiState.Success)
            {

                _pockemonUiState.value = currentState.copy(data = currentState.data + newCards)
            } else
            {
                _pockemonUiState.value =
                    PockemonUiState.Success(
                        newCards
                    )
            }
            currentPage++

        }
    }




}
