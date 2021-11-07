package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.fullweather

sealed class FullWeatherStateEvent{

    class GetCoords: FullWeatherStateEvent()

    object GetCurrentWeatherStateByCityName: FullWeatherStateEvent()

    data class GetCurrentWeatherStateByCoords(
        val lon: Double,
        val lat: Double
    ): FullWeatherStateEvent()

    object GetFullWeatherStateByCityName: FullWeatherStateEvent()

    data class GetFullWeatherStateByCoords(
        val lon: Double,
        val lat: Double
    ): FullWeatherStateEvent()

}