package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.weatherdetail

import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.fullweather.FullWeatherStateEvent

sealed class WeatherDetailStateEvent {

    data class GetRelatedFullWeatherItem(
        val cityName: String?,
        val lon: Double?,
        val lat: Double?,
        val dt: Long
    ): WeatherDetailStateEvent()
}