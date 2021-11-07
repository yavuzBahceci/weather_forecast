package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.weather.fullweather

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.LoadingFullWeatherScreen
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.NothingFound
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.navigation.Screen

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun FullWeatherList(
    loading: Boolean,
    dailys: List<FullWeather.Daily>,
    city: FullWeather.City,
    onNavigateToWeatherDetail: (String) -> Unit,
    darkTheme: Boolean
) {
    if (loading && dailys.isEmpty()) {
        LoadingFullWeatherScreen(imageHeight = 40.dp)
    } else if (dailys.isEmpty()) {
        NothingFound()
    } else {
        LazyColumn {
            itemsIndexed(
                items = dailys
            ) { _, daily ->
                WeatherCard(
                    daily = daily,
                    onClick = {
                        val route =
                            Screen.WeatherDetail.route + "/${daily.dt}" + "?cityName=${city.cityName}" + "&longitude=${city.coord.lon}" + "&latitude=${city.coord.lat}"
                        onNavigateToWeatherDetail(route)
                    },
                    darkTheme = darkTheme
                )
            }
        }
    }
}
