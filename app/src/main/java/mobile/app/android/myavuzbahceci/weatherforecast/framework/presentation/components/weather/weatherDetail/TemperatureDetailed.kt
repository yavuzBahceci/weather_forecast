package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.weather.fullweather

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mobile.app.android.myavuzbahceci.weatherforecast.R
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.theme.Black2

@Composable
fun TemperatureDetailed(
    weather: FullWeather.Daily,
    darkTheme: Boolean
) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.temp.min),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.min_temperature), color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.temp.max),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.now_temperature), color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.temp.max),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.max_temperature), color = Color.White)
        }
    }
    Divider(color = if (darkTheme) Color.White else Black2)
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.feelsLike.day),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.feels_like), color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${weather.humidity} %",
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.humidity), color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = weather.pressure.toString(),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.pressure), color = Color.White)
        }
    }
    Divider(color = if (darkTheme) Color.White else Black2)
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.temp.eve),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.evening_text), color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.temp.morn),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.morning_text), color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.temp.night),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.night_text), color = Color.White)
        }
    }
    Divider(color = if (darkTheme) Color.White else Black2)
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = weather.windDeg.toString(),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.wind_degree), color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = weather.windGust.toString(),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.wind_gust), color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = weather.windSpeed.toString(),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.wind_speed), color = Color.White)
        }
    }

}