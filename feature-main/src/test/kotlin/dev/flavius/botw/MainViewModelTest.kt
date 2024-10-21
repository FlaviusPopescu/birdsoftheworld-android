package dev.flavius.botw

import kotlin.test.Test
import kotlin.test.assertTrue

class MainViewModelTest {
    @Test
    fun `message is not empty`() {
        MainViewModel().run {
            assertTrue { message.isNotEmpty() }
        }
    }
}