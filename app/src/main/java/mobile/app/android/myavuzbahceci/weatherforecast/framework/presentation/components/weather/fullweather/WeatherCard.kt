package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.weather.fullweather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.theme.Black1
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.theme.Grey1
import java.text.SimpleDateFormat
import java.util.*

@Preview
@ExperimentalCoroutinesApi
@Composable
fun WeatherCard(
    daily: FullWeather.Daily,
    onClick: () -> Unit,
    darkTheme: Boolean
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 4.dp,
                top = 4.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(color = if (!darkTheme) Grey1 else Color.Black)
        ,
        elevation = 8.dp,
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp)
        ) {
            Text(
                text = SimpleDateFormat("EEEE, d").format(Date(daily.dt * 1_000)),
                color = if (!darkTheme) Black1 else Color.White,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Image(
                painter = painterResource(daily.forecastIcon()),
                contentDescription = "Forecast icon",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center)
            )
            Text(
                text = formatTemperature(daily.temp.day),
                color =  if (!darkTheme) Black1 else Color.White,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}