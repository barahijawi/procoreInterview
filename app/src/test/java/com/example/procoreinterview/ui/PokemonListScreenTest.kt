package com.example.procoreinterview.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import com.example.procoreinterview.MainActivity
import com.example.procoreinterview.domain.PockemonCard
import com.example.procoreinterview.presentation.viewmodel.PockemonViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class PockemonListScreenTest {


    // Mock ViewModel
    private val mockViewModel = mockk<PockemonViewModel>(relaxed = true)

    // Test rule for launching the activity
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // Mocked Pokémon card data
    private val mockPockemonCards = listOf(
        PockemonCard(id = "1", name = "Pikachu", hp = "50", imageUrlSmall = "", imageUrlLarge = "", superType = "",
                     types = emptyList(), subTypes = emptyList()),
        PockemonCard(id = "2", name = "Charizard", hp = "100", imageUrlSmall = "", imageUrlLarge = "", superType =
        "", types = emptyList(), subTypes = emptyList())
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        // Mock the pockemonCards StateFlow to return our test data
        val pokemonStateFlow = MutableStateFlow(mockPockemonCards)
        every { mockViewModel.pockemonCards } returns pokemonStateFlow
    }

    @Test
    fun testSortingByHp() {
        // Launch the app and verify the sorting dropdown is displayed
        composeTestRule.onNodeWithText("Sort by: None").assertIsDisplayed()

        // Open the sorting dropdown
        composeTestRule.onNodeWithText("Sort by: None").performClick()

        // Click "Sort by HP" in the dropdown
        composeTestRule.onNodeWithText("Sort by HP").performClick()

        // Verify that the Pokémon cards are sorted by HP in the UI (in our mocked data)
        composeTestRule.onNodeWithText("Charizard").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pikachu").assertIsDisplayed()
    }
}
