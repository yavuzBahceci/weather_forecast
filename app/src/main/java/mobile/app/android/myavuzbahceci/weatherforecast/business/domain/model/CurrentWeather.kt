package mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    val coords: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val cityId: Int,
    val cityName: String,
    val cod: Int
) {
    data class Coord(
        val lon: Double,
        val lat: Double
    )

    data class Weather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    data class Main(
        val temp: Double,
        val feelsLike: Double,
        val tempMin: Double,
        val tempMax: Double,
        val pressure: Int,
        val humidity: Int
    )

    data class Wind(
        val speed: Double,
        val deg: Int,
        val gust: Double
    )

    data class Clouds(
        val all: Int
    )

    data class Sys(
        val type: Int,
        val id: Int,
        val message: Double,
        val country: String,
        val sunrise: Long,
        val sunset: Long
    )
}