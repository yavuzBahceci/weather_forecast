package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.api

import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.CurrentWeatherResponse
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.FullWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApi {

    @GET("weather?units=metric&mode=json")
    suspend fun getCurrentWeatherWithCoords(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("APPID") appid: String,
    ): CurrentWeatherResponse

    @GET("weather?units=metric&mode=json")
    suspend fun getCurrentWeatherConditionsWithCityName(
        @Query("q") cityName: String,
        @Query("APPID") appid: String,
    ): CurrentWeatherResponse

    @GET("forecast/daily?units=metric&cnt=16&mode=json")
    suspend fun getFullWeatherWithCoords(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("APPID") appid: String,
    ): FullWeatherResponse

    @GET("forecast/daily?units=metric&cnt=16&mode=json")
    suspend fun getFullWeatherWithCityName(
        @Query("q") cityName: String,
        @Query("APPID") appid: String,
    ): FullWeatherResponse

}