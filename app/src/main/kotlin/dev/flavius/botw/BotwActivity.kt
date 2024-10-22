package dev.flavius.botw

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import dev.flavius.botw.navigation.Main
import dev.flavius.botw.navigation.Permissions
import dev.flavius.botw.permissions.PermissionsScreen
import dev.flavius.botw.ui.MainScreen
import dev.flavius.botw.ui.theme.AppTheme

@AndroidEntryPoint
class BotwActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()

                val locationPermissionState = rememberPermissionState(ACCESS_COARSE_LOCATION)
                val preferences = remember { getPreferences(MODE_PRIVATE) }
                var permissionRequestCount by remember {
                    mutableIntStateOf(preferences.getInt(KEY_PERMISSIONS_REQUEST_COUNT, 0))
                }
                val shouldShowPermissionScreen by remember {
                    derivedStateOf {
                        permissionRequestCount < 2 && locationPermissionState.status.isGranted.not()
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = when (shouldShowPermissionScreen) {
                        true -> Permissions
                        false -> Main
                    }
                ) {
                    composable<Permissions> {
                        val dismissPermissions = {
                            navController.popBackStack()
                            navController.navigate(Main)
                        }
                        PermissionsScreen(
                            locationPermissionState,
                            onPermissionRequestAttempted = {
                                permissionRequestCount++
                                preferences.edit {
                                    putInt(KEY_PERMISSIONS_REQUEST_COUNT, permissionRequestCount)
                                }
                                dismissPermissions()
                            },
                            onDismiss = { dismissPermissions() }
                        )
                    }
                    composable<Main> {
                        val mainViewModel = hiltViewModel<MainViewModel>().apply {
                            hasLocationPermission = locationPermissionState.status.isGranted
                        }
                        MainScreen(mainViewModel) {
                            navController.popBackStack()
                            navController.navigate(Permissions)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val KEY_PERMISSIONS_REQUEST_COUNT = "permission_request_count"
    }
}
