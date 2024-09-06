package com.example.procoreinterview.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procoreinterview.data.model.ItemDetailsEntity
import com.example.procoreinterview.data.model.ItemEntity
import com.example.procoreinterview.domain.use_case.GetItemsUseCase
import com.example.procoreinterview.domain.use_case.ItemDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val itemDetailsUseCase: ItemDetailsUseCase
) :ViewModel()
{
    val items: StateFlow<List<ItemEntity>> = getItemsUseCase.getItems()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _itemDetails = MutableStateFlow<ItemDetailsEntity?>(null)
    val itemDetails: StateFlow<ItemDetailsEntity?> get() = _itemDetails

    fun refreshItems(){
        viewModelScope.launch {
            try
            {
                getItemsUseCase.refreshItems()

                Log.d("ItemViewModel", "Items refreshed from API")

            }
            catch(e:Exception)
            {
                Log.e("ItemViewModel", "Error refreshing items", e)

            }
            }
        }

    fun fetchItemDetails(itemId: String)
    {
        viewModelScope.launch {
            try
            {
                // Call the suspend function within a coroutine
                val details = itemDetailsUseCase(itemId)
                _itemDetails.value = details  // Update the StateFlow with the result
            } catch (e: Exception)
            {
                // Handle exceptions here (e.g., log the error)
                _itemDetails.value = null  // Optionally handle errors by setting null
            }
        }
    }
    }
