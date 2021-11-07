package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao

import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.database.WeatherDataBaseFake
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model.FullWeatherEntity

class FullWeatherDaoFake(private val appDatabaseFake: WeatherDataBaseFake): FullWeatherDao {
    override suspend fun insertFullWeather(fullWeatherEntity: FullWeatherEntity): Long {
        appDatabaseFake.fullWeathers.add(fullWeatherEntity)
        return 1
    }

    override suspend fun getFullWeatherWithCityName(cityName: String): FullWeatherEntity? {
        return appDatabaseFake.fullWeathers.find { it.cityName == cityName }
    }

    override suspend fun getFullWeatherWithCoords(coordsSerialized: String): FullWeatherEntity? {
        return appDatabaseFake.fullWeathers.find { it.coordsSerialized == coordsSerialized }
    }
}