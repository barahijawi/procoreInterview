package com.example.procoreinterview.domain.use_case

import com.example.procoreinterview.data.model.ItemDetailsEntity
import com.example.procoreinterview.data.model.ItemEntity
import com.example.procoreinterview.data.repository.ItemRepository
import javax.inject.Inject

class ItemDetailsUseCase @Inject constructor(
    private val itemRepository: ItemRepository
)
{
   suspend operator fun invoke(itemId:String):ItemDetailsEntity{
      itemRepository.fetchItemDetailsFromApi(itemId)
       val itemDetailsEntity : ItemDetailsEntity = itemRepository.getItemFromDb(itemId)
       return itemDetailsEntity
   }
}