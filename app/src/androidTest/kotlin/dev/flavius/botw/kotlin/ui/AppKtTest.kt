package dev.flavius.botw.kotlin.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import dev.flavius.botw.kotlin.MainViewModel
import org.junit.Rule
import org.junit.Test

class AppKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val mainViewModel = MainViewModel()

    @Test
    fun showsMessage() {
        composeTestRule.run {
            setContent {
                App(mainViewModel)
            }
            onNodeWithText(mainViewModel.message, substring = true).assertExists()
        }
    }
}
