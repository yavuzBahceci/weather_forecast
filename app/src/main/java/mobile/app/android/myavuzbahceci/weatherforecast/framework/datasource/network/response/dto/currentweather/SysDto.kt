package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.currentweather

import com.google.gson.annotations.SerializedName

data class SysDto(
    @SerializedName("type")
    val type: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: Double,
    @SerializedName("country")
    val country: String,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long
)