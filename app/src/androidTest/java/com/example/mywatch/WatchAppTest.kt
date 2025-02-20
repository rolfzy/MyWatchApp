package com.example.mywatch

import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class WatchAppTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun testHomeScreenDisplayItems(){
        composeTestRule.onNodeWithText("Rolex Submariner").assertExists()
    }

    @Test
    fun testSearchFilter(){
        composeTestRule.onNode(hasText("Cari Arloji...") and hasSetTextAction()).performTextInput("Rolex")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Rolex Submariner").assertExists()


        composeTestRule.onNodeWithText("Swatch").assertDoesNotExist()
    }


    @Test
    fun testNavigationToDetailAndBack(){
        composeTestRule.onNodeWithText("Rolex Submariner").performClick()
        composeTestRule.onNodeWithText("Rolex Submariner").assertExists()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNode(hasText("Cari Arloji...") and hasSetTextAction()).assertExists()

    }

    @Test
    fun testAddAndRemoveFavorite(){
        composeTestRule.onNodeWithText("Rolex Submariner").performClick()
        composeTestRule.onNodeWithText("Tambah ke favorite").performClick()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithContentDescription("Favorites_page").performClick()
        composeTestRule.onNodeWithText("Rolex Submariner").assertExists()
        composeTestRule.onNodeWithText("Hapus dari favorite").performClick()
        composeTestRule.onNodeWithText("Rolex Submariner").assertDoesNotExist()



    }

}