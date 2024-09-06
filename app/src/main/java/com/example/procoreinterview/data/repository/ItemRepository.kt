package com.example.procoreinterview.data.repository

import android.util.Log
import com.example.procoreinterview.data.api.ApiService
import com.example.procoreinterview.data.db.ItemDao
import com.example.procoreinterview.data.db.ItemDetailsDao
import com.example.procoreinterview.data.model.ItemDetailsEntity
import com.example.procoreinterview.data.model.ItemEntity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val apiService :ApiService,
   private val  itemDao: ItemDao,
    private val itemDetailsDao: ItemDetailsDao
)
{

    suspend fun fetchItemsFromApi(){

        try
        {
            val apiItems = apiService.getItems()
            Log.d("ItemRepository", "API items loaded: $apiItems")

            val itemEntities = apiItems.items.map {
                ItemEntity(it.id, it.name,it.description,it.image)
            }
            itemDao.insertAll(itemEntities)
            Log.d("ItemRepository", "Items inserted into DB: $itemEntities")
        }
        catch(e:Exception)
        {
            Log.e("ItemRepository", "Error loading items", e)

        }
    }

    fun getItemsFromDb():Flow<List<ItemEntity>> = itemDao.getAllItems()

    suspend fun fetchItemDetailsFromApi(itemId:String){
        try
        {
            val apiItemDetails = apiService.getItemDetails(itemId)
            itemDetailsDao.insert(ItemDetailsEntity(itemId= apiItemDetails.id, additionalInfo = apiItemDetails
                .description, moreDetails = apiItemDetails.image ) )

        }catch (e:Exception){
            Log.e("ItemRepository","error fetching from API")
        }
    }

    fun getItemFromDb(itemId:String):ItemDetailsEntity = itemDetailsDao.getItemDetails(itemId = itemId)
}