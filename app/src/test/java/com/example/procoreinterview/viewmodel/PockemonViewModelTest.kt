package com.example.procoreinterview.viewmodel

import com.example.procoreinterview.domain.GetPockemonCardUseCase
import com.example.procoreinterview.domain.PockemonCard
import com.example.procoreinterview.presentation.viewmodel.PockemonViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class PockemonViewModelTest {

    private lateinit var getPockemonCardUseCase: GetPockemonCardUseCase
    private lateinit var viewModel: PockemonViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)

        getPockemonCardUseCase = mockk()

        // Mock the suspending function
        coEvery { getPockemonCardUseCase() } returns listOf(
            PockemonCard(id = "1", name = "Pikachu", hp = "100", imageUrl = "some_url")
        )

        viewModel = PockemonViewModel(getPockemonCardUseCase)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher to its original state
        Dispatchers.resetMain()
    }

    @Test
    fun `test viewmodel fetches data and updates state`() = runTest {
        // Fetch cards in the ViewModel
        viewModel.fetchPockemonCards()

        // Move time forward for the coroutine to complete
        advanceUntilIdle()

        // Assert the state is updated
        assertEquals(
            listOf(PockemonCard(id = "1", name = "Pikachu", hp = "100", imageUrl = "some_url")),
            viewModel.pockemonCards.value
        )
    }
}
