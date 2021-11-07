package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.fullweather

import com.google.gson.annotations.SerializedName

data class FeelsLikeDto(
    @SerializedName("day")
    val day: Double,
    @SerializedName("night")
    val night: Double,
    @SerializedName("eve")
    val eve: Double,
    @SerializedName("morn")
    val morn: Double
)