package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.weather.fullweather

import androidx.compose.foundation.layout.*
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
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.theme.Black1

@Composable
fun TemperatureSummary(
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
                color = if (darkTheme) Color.White else Black1
            )
            Text(text = stringResource(R.string.min_temperature), color = if (darkTheme) Color.White else Black1)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.temp.max),
                fontSize = 18.sp,
                color = if (darkTheme) Color.White else Black1
            )
            Text(text = stringResource(R.string.now_temperature), color = if (darkTheme) Color.White else Black1)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.temp.max),
                fontSize = 18.sp,
                color = if (darkTheme) Color.White else Black1
            )
            Text(text = stringResource(R.string.max_temperature), color = if (darkTheme) Color.White else Black1)
        }
    }
}