package com.example.procoreinterview.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procoreinterview.data.database.MinMaxHp
import com.example.procoreinterview.domain.GetCachedPockemonCardUseCase
import com.example.procoreinterview.domain.GetMinMaxHpUseCase
import com.example.procoreinterview.domain.GetPockemonCardUseCase
import com.example.procoreinterview.domain.PockemonCard
import com.example.procoreinterview.domain.SavePokemonCardUseCase
import com.example.procoreinterview.util.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PockemonViewModel @Inject constructor(
    private val getPockemonCardUseCase: GetPockemonCardUseCase,
    private val getCachedPokemonCardUseCase: GetCachedPockemonCardUseCase,
    private val savePockemonCardsUseCase: SavePokemonCardUseCase,
    private val getMinMaxHpUseCase: GetMinMaxHpUseCase
) : ViewModel()
{
    private val _pockemonCards = MutableStateFlow<List<PockemonCard>>(emptyList())
    val pockemonCards : StateFlow<List<PockemonCard>> get()= _pockemonCards

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState : StateFlow<String?> get() = _errorState

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    // Function to load Pokémon cards based on network availability
    fun loadPockemonCards(isNetworkAvailable: Boolean) {
        viewModelScope.launch {
            _loading.value = true

            try {
                // 1. Try to fetch cached data from the database
                fetchCachedPokemonCardsFromDB()

                // 2. If the network is available, fetch fresh data from the API and update RoomDB and UI
                if (isNetworkAvailable) {
                    fetchPockemonCardsFromAPI()
                }
            } catch (e: Exception) {
                _errorState.value = "Failed to load Pokémon cards"
            } finally {
                _loading.value = false
            }
        }
    }
    fun sortPokemonCards(option: SortOption) {
        _pockemonCards.update { currentCards ->
            when (option) {
                SortOption.TYPE -> currentCards.sortedBy { it.types.firstOrNull() ?: "" }
                SortOption.HP -> currentCards.sortedBy { it.hp.toIntOrNull() ?: 0 }
                SortOption.NONE -> currentCards // No sorting applied
            }
        }
    }


    // Fetch Pokémon cards from API and update UI
    private fun fetchPockemonCardsFromAPI() {
        viewModelScope.launch {

            try {
                val cards = getPockemonCardUseCase() // Use the API use case
                savePockemonCardsUseCase(cards)        // Save the fetched cards to RoomDB

                _pockemonCards.value = cards        // Update UI
            } catch (e: Exception) {
                _errorState.value = "Failed to fetch Pokémon cards from API"
            }

        }
    }

    // Fetch cached Pokémon cards from RoomDB and update UI
    private fun fetchCachedPokemonCardsFromDB() {
        viewModelScope.launch {
            getCachedPokemonCardUseCase().collect { cards ->
                _pockemonCards.value = cards // Update UI with cached data
            }
        }
    }


    private val _minMaxHp = MutableStateFlow(MinMaxHp(0, 0))
    val minMaxHp: StateFlow<MinMaxHp> get() = _minMaxHp


    // Load min and max HP values from the RoomDB
    fun loadMinMaxHp() {
        viewModelScope.launch {
            _loading.value = true // Start loading
            try {
                val minMaxHpValues = getMinMaxHpUseCase()
                _minMaxHp.value = minMaxHpValues
            } catch (e: Exception) {
                _errorState.value = "Failed to load HP range"
            } finally {
                _loading.value = false // Stop loading
            }
        }
    }

    // Apply HP filter and update the UI
    fun applyHpFilter(minHp: Int, maxHp: Int) {
        viewModelScope.launch {
            _loading.value = false // Start loading
            try {
                val filteredCards = getCachedPokemonCardUseCase().map { cards ->
                    cards.filter { card ->
                        val hp = card.hp.toIntOrNull() ?: 0
                        hp in minHp..maxHp
                    }
                }.first()
                _pockemonCards.value = filteredCards
            } catch (e: Exception) {
                _errorState.value = "Failed to apply filter"
            } finally {
                _loading.value = false // Stop loading
            }
        }
    }
}