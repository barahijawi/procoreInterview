package com.example.procoreinterview.viewmodel

import com.example.procoreinterview.data.repository.PockemonRepository
import com.example.procoreinterview.domain.GetCachedPockemonCardUseCase
import com.example.procoreinterview.domain.GetMinMaxHpUseCase
import com.example.procoreinterview.domain.PockemonCard
import com.example.procoreinterview.domain.GetPockemonCardUseCase
import com.example.procoreinterview.domain.SavePokemonCardUseCase
import com.example.procoreinterview.presentation.viewmodel.PockemonViewModel
import com.example.procoreinterview.util.SortOption
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain


@OptIn(ExperimentalCoroutinesApi::class)
class PockemonViewModelWithMockkTest {

    private lateinit var viewModel: PockemonViewModel
    private lateinit var mockRepository: PockemonRepository
    private lateinit var getPockemonCardUseCase: GetPockemonCardUseCase
    private lateinit var getCachedPokemonCardUseCase: GetCachedPockemonCardUseCase
    private lateinit var getMinMaxHpUseCase: GetMinMaxHpUseCase
    private lateinit var savePockemonCardsUseCase: SavePokemonCardUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())

        // Mock the repository and use cases
        mockRepository = mockk()
        getPockemonCardUseCase = mockk()
        getCachedPokemonCardUseCase = mockk()
        getMinMaxHpUseCase = mockk()
        savePockemonCardsUseCase = mockk()

        // Pass the mocked use cases into the ViewModel
        viewModel = PockemonViewModel(
            getPockemonCardUseCase,
            getCachedPokemonCardUseCase,
            savePockemonCardsUseCase,
            getMinMaxHpUseCase
        )
    }

    @Test
    fun `test fetchPokemonCards with mock repository`() = runTest {
        // Given: A mocked response from the repository
        val mockCards = listOf(
            PockemonCard(id = "1", name = "Pikachu", hp = "50", imageUrlSmall = "", imageUrlLarge = "", superType = "",
                         types = emptyList(), subTypes = emptyList()),
            PockemonCard(id = "2", name = "Charizard", hp = "100", imageUrlSmall = "", imageUrlLarge = "", superType = "",
                         types = emptyList(), subTypes = emptyList())
        )

        // Mock the behavior for fetching from the network and saving to DB
        coEvery { getPockemonCardUseCase() } returns mockCards
        coEvery { savePockemonCardsUseCase(any()) } returns Unit

        // Mock the cached cards retrieval
        val mockCachedCardsFlow = MutableStateFlow(mockCards)
        coEvery { getCachedPokemonCardUseCase() } returns mockCachedCardsFlow

        // When: The ViewModel loads the cards (network fetch)
        viewModel.loadPockemonCards(isNetworkAvailable = true)

        // Make sure to advance the coroutine until all tasks are finished
        advanceUntilIdle()

        // Then: Verify that the ViewModel holds the mocked data
        val cards = viewModel.pockemonCards.first()
        assertThat(cards).isEqualTo(mockCards)
    }
}
