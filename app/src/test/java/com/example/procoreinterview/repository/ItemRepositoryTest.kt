package com.example.procoreinterview.repository

import com.example.procoreinterview.data.api.ApiService
import com.example.procoreinterview.data.db.ItemDao
import com.example.procoreinterview.data.model.ApiItem
import com.example.procoreinterview.data.model.ItemEntity
import com.example.procoreinterview.data.repository.ItemRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class ItemRepositoryTest {
//
//    private lateinit var repository: ItemRepository
//    private lateinit var mockApiService: ApiService
//    private lateinit var mockItemDao: ItemDao
//
//    @Before
//    fun setup() {
//        // Initialize mocks
//        mockApiService = mock(ApiService::class.java)
//        mockItemDao = mock(ItemDao::class.java)
//        repository = ItemRepository(mockApiService, mockItemDao)
//    }
//
//    @Test
//    fun `test fetchItemsFromApi inserts data into database`() = runBlocking {
//        // Mock API response
//        val apiItems = listOf(ApiItem(1, "Item 1"), ApiItem(2, "Item 2"))
//        `when`(mockApiService.getItems()).thenReturn(apiItems)
//
//        // Call the repository function
//        repository.fetchItemsFromApi()
//
//        // Verify the items were inserted into the database
//        val expectedEntities = listOf(
//            ItemEntity(1, "Item 1"),
//            ItemEntity(2, "Item 2")
//        )
//        verify(mockItemDao).insertAll(expectedEntities)
//    }
//
//    @Test
//    fun `test getItemsFromDb returns items from database`() = runBlocking {
//        // Mock the database response
//        val dbItems = listOf(
//            ItemEntity(1, "Item 1"),
//            ItemEntity(2, "Item 2")
//        )
//        val flowOfItems: Flow<List<ItemEntity>> = flowOf(dbItems)
//        `when`(mockItemDao.getAllItems()).thenReturn(flowOfItems)
//
//        // Get the items from the repository
//        val result = repository.getItemsFromDb()
//
//        // Collect the flow and assert the result using Google Truth
//        result.collect { items ->
//            assertThat(items).isNotNull()
//            assertThat(items.size).isEqualTo(2)
//            assertThat(items[0].name).isEqualTo("Item 1")
//            assertThat(items[1].name).isEqualTo("Item 2")
//        }
//    }
}