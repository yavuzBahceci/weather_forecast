package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.currentweather

import com.google.gson.annotations.SerializedName

data class CoordDto (
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("lat")
    val lat: Double
)