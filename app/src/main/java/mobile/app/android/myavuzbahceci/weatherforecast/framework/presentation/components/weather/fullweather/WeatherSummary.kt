package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.weather.fullweather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.fullweather.background

@Composable
fun WeatherSummary(weather: FullWeather.Daily, cityName: String) {
    Box {
        Image(
            painter = painterResource(id = weather.background()),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            contentScale = ContentScale.FillWidth,
        )
        Column(
            Modifier
                .padding(top = 48.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = formatTemperature(weather.temp.day), fontSize = 48.sp, color = Color.White)
            Text(text = weather.weather.first().main, fontSize = 28.sp, color = Color.White)
            Text(text = cityName, fontSize = 18.sp, color = Color.White)
        }
    }
}