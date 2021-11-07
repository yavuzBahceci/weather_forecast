package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.weather.fullweather

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mobile.app.android.myavuzbahceci.weatherforecast.R
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@SuppressLint("SimpleDateFormat")
@Composable
fun SixTeenDayForecast(forecast: List<FullWeather.Daily>) {
    LazyColumn {
        items(forecast) { dayForecast ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
            ) {
                Text(
                    text = SimpleDateFormat("EEEE").format(Date(dayForecast.dt * 1_000)),
                    color = Color.Blue,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Image(
                    painter = painterResource(dayForecast.forecastIcon()),
                    contentDescription = "Forecast icon",
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
                Text(
                    text = formatTemperature(dayForecast.temp.day),
                    color = Color.Blue,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}



fun FullWeather.Daily.forecastIcon(): Int {
    val conditions = weather.first().main
    return when {
        conditions.contains("cloud", ignoreCase = true) -> R.drawable.clouds
        conditions.contains("rain", ignoreCase = true) -> R.drawable.rainy
        else -> R.drawable.sunny
    }
}

@Composable
fun formatTemperature(temperature: Double): String {
    return stringResource(R.string.temperature_degrees, temperature.roundToInt())
}
