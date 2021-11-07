package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.database

import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model.CurrentWeatherEntity
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model.FullWeatherEntity

class WeatherDataBaseFake {

    // fake for full weather table in db
    val fullWeathers = mutableListOf<FullWeatherEntity>()

    // fake for current weather table in db
    val currentWeather = mutableListOf<CurrentWeatherEntity>()

}