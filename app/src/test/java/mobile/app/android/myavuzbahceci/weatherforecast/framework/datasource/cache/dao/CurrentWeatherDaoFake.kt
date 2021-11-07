package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao

import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.database.WeatherDataBaseFake
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model.CurrentWeatherEntity

class CurrentWeatherDaoFake(private val appDatabaseFake: WeatherDataBaseFake): CurrentWeatherDao {
    override suspend fun insertCurrentWeather(currentWeatherEntity: CurrentWeatherEntity): Long {
        appDatabaseFake.currentWeather.add(currentWeatherEntity)
        return 1 // return success
    }

    override suspend fun getCurrentWeatherWithCityName(cityName: String): CurrentWeatherEntity? {
        return appDatabaseFake.currentWeather.find { it.cityName == cityName }
    }

    override suspend fun getCurrentWeatherWithCoords(coordsSerialized: String): CurrentWeatherEntity? {
        return appDatabaseFake.currentWeather.find { it.coordSerialized == coordsSerialized }
    }

    override suspend fun searchCities(query: String): CurrentWeatherEntity? {
        return appDatabaseFake.currentWeather.firstOrNull()
    }
}