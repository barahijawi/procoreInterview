package com.example.procoreinterview.viewmodel

import com.example.procoreinterview.domain.GetPockemonCardUseCase
import com.example.procoreinterview.domain.PockemonCard
import com.example.procoreinterview.presentation.viewmodel.PockemonUiState
import com.example.procoreinterview.presentation.viewmodel.PockemonViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class PockemonViewModelTest {

    private lateinit var getPockemonCardUseCase: GetPockemonCardUseCase
    private lateinit var viewModel: PockemonViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        getPockemonCardUseCase = mockk()

        // Mock both methods of the use case
        coEvery { getPockemonCardUseCase(any(), any()) } returns listOf(
            PockemonCard(id = "1", name = "Pikachu", hp = "100", imageUrl = "some_url")
        )

        coEvery { getPockemonCardUseCase.getCardsCount() } returns 100

        viewModel = PockemonViewModel(getPockemonCardUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test viewmodel fetches data and updates state`() = runTest {
        // Fetch cards in the ViewModel
        viewModel.fetchPockemonCards()

        // Move time forward for the coroutine to complete
        advanceUntilIdle()

        println(viewModel.pockemonUiState.value)

        // Assert the state is updated to success
        assertTrue(viewModel.pockemonUiState.value is PockemonUiState.Success)
        val fetchedData = (viewModel.pockemonUiState.value as PockemonUiState.Success).data
        assertEquals(1, fetchedData.size)
        assertEquals("Pikachu", fetchedData[0].name)
    }
}
