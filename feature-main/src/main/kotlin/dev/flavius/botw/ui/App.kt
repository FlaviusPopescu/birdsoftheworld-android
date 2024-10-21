package dev.flavius.botw.kotlin.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.flavius.botw.MainViewModel

@Composable
fun App(mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box {
            Text(
                "Hello, from ${mainViewModel.message}!",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}