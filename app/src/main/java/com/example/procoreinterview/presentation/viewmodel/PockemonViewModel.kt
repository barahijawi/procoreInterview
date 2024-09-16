package com.example.procoreinterview.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procoreinterview.domain.GetPockemonCardUseCase
import com.example.procoreinterview.domain.PockemonCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PockemonViewModel @Inject constructor(
    private val getPockemonCardUseCase: GetPockemonCardUseCase
) : ViewModel()
{
    private val _pockemonCard = MutableStateFlow<List<PockemonCard>>(emptyList())
    val pockemonCards : StateFlow<List<PockemonCard>> get()= _pockemonCard

    init
    {
        fetchPockemonCards()
    }

     fun fetchPockemonCards(){
        viewModelScope.launch {
            val cards = getPockemonCardUseCase()
            _pockemonCard.value = cards
        }
    }
}