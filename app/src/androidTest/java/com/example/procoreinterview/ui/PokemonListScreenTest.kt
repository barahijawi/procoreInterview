package com.example.procoreinterview.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.procoreinterview.presentation.screens.PockemonListScreen

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PockemonListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPockemonListScreen_displaySortOptions() {
        composeTestRule.setContent {
            // Mock viewModel and onCardClick logic if necessary
            PockemonListScreen(onCardClick = {})
        }

        // Verify sorting text is displayed
        composeTestRule.onNodeWithText("Sort by: None").assertIsDisplayed()

        // Click on "Sort by: None"
        composeTestRule.onNodeWithText("Sort by: None").performClick()

        // Click on "Sort by HP" in the dropdown
        composeTestRule.onNodeWithText("Sort by HP").performClick()

        // Verify that the Pokémon cards are displayed
        composeTestRule.onNodeWithText("Pikachu").assertIsDisplayed()
        composeTestRule.onNodeWithText("Charizard").assertIsDisplayed()
    }

    @Test
    fun testPockemonListScreen_loadingState() {
        composeTestRule.setContent {
            PockemonListScreen(onCardClick = {})
        }

        // Simulate loading state (since the shimmer is used in the actual code)
        composeTestRule.onNodeWithText("Shimmer loading...").assertIsDisplayed()
    }

    @Test
    fun testPockemonListScreen_errorMessage() {
        composeTestRule.setContent {
            PockemonListScreen(onCardClick = {})
        }

        // Simulate an error state
        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
    }
}
