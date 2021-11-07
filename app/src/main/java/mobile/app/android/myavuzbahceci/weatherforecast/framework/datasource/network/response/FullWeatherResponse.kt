package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response

import com.google.gson.annotations.SerializedName
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.fullweather.CityDto
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.fullweather.DailyDto

data class FullWeatherResponse(
    @SerializedName("city")
    val cityDto: CityDto,
    @SerializedName("list")
    val daily: List<DailyDto>,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("message")
    val message: Double,
    @SerializedName("cnt")
    val count: Int
)