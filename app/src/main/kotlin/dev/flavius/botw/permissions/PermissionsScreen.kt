package dev.flavius.botw.permissions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.PermissionState
import dev.flavius.botw.R

@Composable
fun PermissionsScreen(
    permissionState: PermissionState,
    modifier: Modifier = Modifier,
    onPermissionRequestAttempted: () -> Unit,
    onDismiss: () -> Unit,
) {
    Surface {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    stringResource(R.string.permissions_title),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    stringResource(R.string.permissions_description),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painterResource(R.drawable.kingfisher),
                        contentDescription = stringResource(R.string.permissions_image),
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.height(240.dp)
                    )
                    Button(
                        onClick = {
                            permissionState.launchPermissionRequest()
                            onPermissionRequestAttempted()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            stringResource(R.string.permissions_coarse_location_button_content_description)
                        )
                        Text(stringResource(R.string.permissions_coarse_location_button))
                    }
                }
                Text(
                    stringResource(R.string.permissions_description_secondary),
                    style = MaterialTheme.typography.bodyLarge,
                )
                TextButton(
                    onDismiss,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) { Text("Continue without") }
            }
        }

    }
}
