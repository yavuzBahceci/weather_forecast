package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.datastore.SettingsDataStore
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.LocationPermissionDetails
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.LocationPermissionNotAvailable
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.navigation.Screen
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.fullweather.FullWeatherScreen
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.fullweather.FullWeatherViewModel
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.weatherdetail.WeatherDetailScreen
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.weatherdetail.WeatherDetailViewModel
import mobile.app.android.myavuzbahceci.weatherforecast.framework.util.ConnectivityManager
import javax.inject.Inject

@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var settingsDataStore: SettingsDataStore

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnection(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnection(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val permission =
                rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

            PermissionRequired(
                permissionState = permission,
                permissionNotGrantedContent = { LocationPermissionDetails(onContinueClick = permission::launchPermissionRequest) },
                permissionNotAvailableContent = { LocationPermissionNotAvailable() }
            ) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.FullWeather.route) {
                    composable(route = Screen.FullWeather.route) { navBackStackEntry ->
                        val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val viewModel: FullWeatherViewModel = viewModel(
                            LocalViewModelStoreOwner.current!!,
                            "FullWeatherViewModel",
                            factory
                        )
                        FullWeatherScreen(
                            isDarkTheme = settingsDataStore.isDark.value,
                            connectivityManager.isNetworkAvailable.value,
                            onToggleTheme = settingsDataStore::themeToggle,
                            onToggleLocation = viewModel::onToggleLocation,
                            onNavigateToWeatherDetailScreen = navController::navigate,
                            viewModel = viewModel,
                        )
                    }
                    composable(
                        route = Screen.WeatherDetail.route + "/{dt}" + "?cityName={cityName}" + "&longitude={longitude}" + "&latitude={latitude}",
                        arguments = listOf(
                            navArgument("dt") { type = NavType.LongType },
                            navArgument("cityName"){ type = NavType.StringType },
                            navArgument("longitude"){ type = NavType.FloatType },
                            navArgument("latitude"){ type = NavType.FloatType },
                        )
                    ) { navBackStackEntry ->
                        val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val viewModel: WeatherDetailViewModel = viewModel(
                            LocalViewModelStoreOwner.current!!,
                            "WeatherDetailViewModel",
                            factory
                        )
                        WeatherDetailScreen(
                            isDarkTheme = settingsDataStore.isDark.value,
                            cityName = navBackStackEntry.arguments?.getString("cityName"),
                            longitude = navBackStackEntry.arguments?.getFloat("longitude"),
                            latitude = navBackStackEntry.arguments?.getFloat("latitude"),
                            dt = navBackStackEntry.arguments?.getLong("dt")?: 0L,
                            viewModel = viewModel,
                        )
                    }
                }
            }
        }
    }


}