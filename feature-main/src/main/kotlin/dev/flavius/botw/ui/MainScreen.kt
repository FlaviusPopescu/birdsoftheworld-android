package dev.flavius.botw.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.flavius.botw.MainViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onButton: () -> Unit,
) {
    mainViewModel.hasLocationPermission = true
    Surface(modifier = Modifier.fillMaxSize()) {
        Box {
            Column(modifier = Modifier.align(Alignment.TopCenter)) {
                Text(
                    "Hello, from ${mainViewModel.message}!",
                )
                Button({ onButton()}) { Text("Click me")}
            }
        }
    }
}