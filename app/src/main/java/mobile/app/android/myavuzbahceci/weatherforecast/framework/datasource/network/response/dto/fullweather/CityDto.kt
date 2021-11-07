package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.fullweather

import com.google.gson.annotations.SerializedName
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.currentweather.CoordDto

data class CityDto(
    @SerializedName("id")
    val cityId: Long,
    @SerializedName("name")
    val cityName: String,
    @SerializedName("coord")
    val coord: CoordDto,
    @SerializedName("country")
    val country: String,
    @SerializedName("population")
    val population: Int,
    @SerializedName("timezone")
    val timezone: Double
)
