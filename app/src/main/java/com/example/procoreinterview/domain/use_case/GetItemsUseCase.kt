package com.example.procoreinterview.domain.use_case

import com.example.procoreinterview.data.model.ItemEntity
import com.example.procoreinterview.data.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val repository: ItemRepository
)
{
    //fetch items from API then store into DB
    suspend fun refreshItems()
    {
        try
        {
            repository.fetchItemsFromApi()
        }
        catch(e :Exception)
        {
            print(e.message)
        }
    }

    fun getItems(): Flow<List<ItemEntity>>
    {
        return repository.getItemsFromDb()
    }


}