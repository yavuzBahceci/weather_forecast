package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model.FullWeatherEntity

@Dao
interface FullWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFullWeather(fullWeatherEntity: FullWeatherEntity): Long

    @Query("SELECT * FROM full_weather WHERE city_name = :cityName")
    suspend fun getFullWeatherWithCityName(cityName: String): FullWeatherEntity?

    @Query("SELECT * FROM full_weather WHERE coords = :coordsSerialized")
    suspend fun getFullWeatherWithCoords(coordsSerialized: String): FullWeatherEntity?
}