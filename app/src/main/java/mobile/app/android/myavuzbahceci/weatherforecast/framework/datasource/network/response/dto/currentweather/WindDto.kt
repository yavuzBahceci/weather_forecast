package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.currentweather

import com.google.gson.annotations.SerializedName

data class WindDto(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Double
)