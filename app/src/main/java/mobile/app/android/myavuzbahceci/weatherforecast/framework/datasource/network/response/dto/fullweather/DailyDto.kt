package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.fullweather

import com.google.gson.annotations.SerializedName
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.common.WeatherDto

data class DailyDto(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("moonrise")
    val moonrise: Int,
    @SerializedName("moonset")
    val moonset: Int,
    @SerializedName("moon_phase")
    val moonPhase: Double,
    @SerializedName("temp")
    val tempDto: TempDto,
    @SerializedName("feels_like")
    val feelsLikeDto: FeelsLikeDto,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("weather")
    val weatherDto: List<WeatherDto>,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("uvi")
    val uvi: Double,
    @SerializedName("rain")
    val rain: Double
)