package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response

import com.google.gson.annotations.SerializedName
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.common.WeatherDto
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.currentweather.*

data class CurrentWeatherResponse(
    @SerializedName("coord")
    val coordDto: CoordDto,
    @SerializedName("weather")
    val weatherDto: List<WeatherDto>,
    @SerializedName("base")
    val base: String,
    @SerializedName("main")
    val mainDto: MainDto,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind")
    val windDto: WindDto,
    @SerializedName("clouds")
    val cloudsDto: CloudsDto,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sys")
    val sysDto: SysDto,
    @SerializedName("timezone")
    val timezone: Long,
    @SerializedName("id")
    val cityId: Int,
    @SerializedName("name")
    val cityName: String,
    @SerializedName("cod")
    val cod: Int
)