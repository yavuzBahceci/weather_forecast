package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.CurrentWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.FullWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model.CurrentWeatherEntity
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model.FullWeatherEntity

@Database(entities = [FullWeatherEntity::class, CurrentWeatherEntity::class], version = 1)
abstract class WeatherForecastDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun fullWeatherDao(): FullWeatherDao

    companion object {
        const val DATABASE_NAME: String = "weather_forecast_db"
    }
}