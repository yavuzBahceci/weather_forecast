package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.fullweather

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mobile.app.android.myavuzbahceci.weatherforecast.R
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.SearchAppBar
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.weather.fullweather.FullWeatherList
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.weather.fullweather.TemperatureSummary
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.weather.fullweather.WeatherSummary
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.theme.*


@SuppressLint("MissingPermission")
@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun FullWeatherScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onToggleTheme: () -> Unit,
    onToggleLocation: () -> Unit,
    onNavigateToWeatherDetailScreen: (String) -> Unit,
    viewModel: FullWeatherViewModel,
) {

    val forecastList = viewModel.dailyWeatherList.value

    val currentFetched = viewModel.currentFetched.value

    val city: FullWeather.City? = viewModel.currentCity.value

    val currentWeather = viewModel.currentWeather.value

    val coords = viewModel.coords.value

    val query = viewModel.query.value

    val selectedCity = viewModel.selectedCity.value

    val loading = viewModel.loading.value

    val dialogQueue = viewModel.dialogQueue

    val scaffoldState = rememberScaffoldState()

    if (city == null && coords != null && !currentFetched) {
        viewModel.currentFetched.value = true
        viewModel.onTriggerEvent(
            FullWeatherStateEvent.GetCurrentWeatherStateByCoords(
                coords.longitude,
                coords.latitude
            )
        )
        viewModel.onTriggerEvent(
            FullWeatherStateEvent.GetFullWeatherStateByCoords(
                coords.longitude,
                coords.latitude
            )
        )
    }
    AppTheme(
        displayProgressBar = loading,
        scaffoldState = scaffoldState,
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        dialogQueue = dialogQueue.dialogQueue.value,
    ) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    query = query,
                    onQueryChanged = viewModel::onQueryChanged,
                    onExecuteSearch = {
                        viewModel.apply {
                            onTriggerEvent(FullWeatherStateEvent.GetFullWeatherStateByCityName)
                            onTriggerEvent(FullWeatherStateEvent.GetCurrentWeatherStateByCityName)
                        }
                    },
                    cities = getSampleCities(),
                    selectedCity = selectedCity,
                    onSelectedCityChanged = viewModel::onSelectedCityChanged,
                    onToggleTheme = { onToggleTheme() },
                    onToggleLocation = { onToggleLocation() }
                )
            },
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            },
        ) {
            city?.let {
                if (forecastList.isNotEmpty()) {
                    Column() {
                        WeatherSummary(weather = forecastList[0], city.cityName)
                        TemperatureSummary(forecastList[0], darkTheme = isDarkTheme)
                        Divider(color = if (isDarkTheme) Color.White else Black2)
                        FullWeatherList(
                            loading = loading,
                            forecastList,
                            city,
                            onNavigateToWeatherDetail = onNavigateToWeatherDetailScreen,
                            darkTheme = isDarkTheme
                        )
                    }
                }
            }
        }
    }
}

@DrawableRes
fun FullWeather.Daily.background(): Int {
    val conditions = weather.first().main
    return when {
        conditions.contains("cloud", ignoreCase = true) -> R.drawable.forest_cloudy
        conditions.contains("rain", ignoreCase = true) -> R.drawable.forest_rainy
        else -> R.drawable.forest_sunny
    }
}

fun FullWeather.Daily.backgroundColour(): Color {
    val conditions = weather.first().main
    return when {
        conditions.contains("cloud", ignoreCase = true) -> Blue
        conditions.contains("rain", ignoreCase = true) -> Grey
        else -> Green
    }
}