package mobile.app.android.myavuzbahceci.weatherforecast.business.repository

import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather


interface WeatherRepository {
    suspend fun getCurrentWeatherWithCityName(cityName: String): CurrentWeather

    suspend fun getCurrentWeatherWithCoords(lon: Double, lat: Double): CurrentWeather

    suspend fun getFullWeatherWithCityName(cityName: String): FullWeather

    suspend fun getFullWeatherWithCoords(lon: Double, lat: Double): FullWeather
}