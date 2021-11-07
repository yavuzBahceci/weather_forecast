package mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model

import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather.*
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.currentweather.CoordDto

data class FullWeather(
    val daily: List<Daily>,
    val city: City,
    val cod: Int? = null,
    val message: Double? = null,
    val count: Int? = null
) {
    data class City(
        val cityId: Long,
        val cityName: String,
        val coord: Coord,
        val country: String,
        val population: Long,
        val timezone: Long
    )

    data class Daily(
        val dt: Long,
        val sunrise: Int,
        val sunset: Int,
        val moonrise: Int,
        val moonset: Int,
        val moonPhase: Double,
        val temp: Temp,
        val feelsLike: FeelsLike,
        val pressure: Int,
        val humidity: Int,
        val dewPoint: Double,
        val windSpeed: Double,
        val windDeg: Int,
        val windGust: Double,
        val weather: List<Weather>,
        val clouds: Int,
        val pop: Double,
        val uvi: Double,
        val rain: Double
    ) {
        data class Temp(
            val day: Double,
            val min: Double,
            val max: Double,
            val night: Double,
            val eve: Double,
            val morn: Double
        )

        data class FeelsLike(
            val day: Double,
            val night: Double,
            val eve: Double,
            val morn: Double
        )

        data class Weather(
            val id: Int,
            val main: String,
            val description: String,
            val icon: String
        )
    }
}