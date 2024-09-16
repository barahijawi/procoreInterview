package com.example.procoreinterview.repository

import com.example.procoreinterview.data.api.PockemonApiService
import com.example.procoreinterview.data.repository.PockemonRepositoryImpl
import com.example.procoreinterview.data.ApiPockemonCard
import com.example.procoreinterview.data.ApiPockemonCardImages
import com.example.procoreinterview.data.PockemonApiResponse
import com.example.procoreinterview.data.database.PockemonCardDao
import com.example.procoreinterview.domain.PockemonCard
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PockemonRepositoryImplTest {

    private lateinit var apiService: PockemonApiService
    private lateinit var repository: PockemonRepositoryImpl
    private lateinit var pockemonDao : PockemonCardDao
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Initialize the mock API service
        apiService = mockk()

        // Mock the API response
        coEvery { apiService.getPockemonCards() } returns PockemonApiResponse(
            data = listOf(
                ApiPockemonCard(
                    id = "1",
                    name = "Pikachu",
                    hp = "100",
                    images = ApiPockemonCardImages(small = "some_image_url")
                )
            )
        )

        // Initialize the repository
        repository = PockemonRepositoryImpl(apiService,pockemonDao)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher to its original state
        Dispatchers.resetMain()
    }

    @Test
    fun `test repository fetches and maps pokemon cards correctly`() = runTest {
        // Fetch Pokemon cards from the repository
        val pokemonCards: List<PockemonCard> = repository.getPockemonCards(1,20)

        // Move time forward for the coroutine to complete
        advanceUntilIdle()

        // Assert that the fetched data is correct
        assertEquals(1, pokemonCards.size)
        assertEquals("1", pokemonCards[0].id)
        assertEquals("Pikachu", pokemonCards[0].name)
        assertEquals("100", pokemonCards[0].hp)
        assertEquals("some_image_url", pokemonCards[0].imageUrl)
    }
}
