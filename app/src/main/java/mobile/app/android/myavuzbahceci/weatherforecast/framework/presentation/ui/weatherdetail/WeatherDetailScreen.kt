package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.weatherdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.weather.fullweather.TemperatureDetailed
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.weather.fullweather.WeatherDetailed
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.theme.Black1
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.theme.Black2
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.fullweather.backgroundColour

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun WeatherDetailScreen(
    isDarkTheme: Boolean,
    cityName: String?,
    longitude: Float?,
    latitude: Float?,
    dt: Long,
    viewModel: WeatherDetailViewModel,
) {
    val current = viewModel.selectedDaily.value

    viewModel.onTriggerEvent(
        WeatherDetailStateEvent.GetRelatedFullWeatherItem(
            cityName,
            longitude?.toDouble(),
            latitude?.toDouble(),
            dt
        )
    )
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(current?.backgroundColour() ?: Color.White)
    ) {
        current?.let {
            WeatherDetailed(weather = it, cityName?: "")
            TemperatureDetailed(it, isDarkTheme)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(current?.backgroundColour() ?: Color.White)
        )
    }
}