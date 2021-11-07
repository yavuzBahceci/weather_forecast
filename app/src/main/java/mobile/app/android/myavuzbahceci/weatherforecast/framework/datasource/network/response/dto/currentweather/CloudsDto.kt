package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.currentweather

import com.google.gson.annotations.SerializedName

data class CloudsDto(
    @SerializedName("all")
    val all: Int
)
