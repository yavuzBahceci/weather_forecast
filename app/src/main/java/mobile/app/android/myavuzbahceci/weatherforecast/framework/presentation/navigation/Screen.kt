package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.navigation

sealed class Screen(
    val route: String,
){
    object FullWeather: Screen("fullWeather")

    object WeatherDetail: Screen("weatherDetail")
}
