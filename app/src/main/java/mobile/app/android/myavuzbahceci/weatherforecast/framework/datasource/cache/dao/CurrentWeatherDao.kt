package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeatherEntity: CurrentWeatherEntity): Long

    @Query("SELECT * FROM current_weather WHERE city_name = :cityName")
    suspend fun getCurrentWeatherWithCityName(cityName: String): CurrentWeatherEntity?

    @Query("SELECT * FROM current_weather WHERE coord = :coordsSerialized")
    suspend fun getCurrentWeatherWithCoords(coordsSerialized: String): CurrentWeatherEntity?

    @Query("""
        SELECT * FROM current_weather 
        WHERE city_name LIKE '%' || :query || '%'
        """)
    suspend fun searchCities(
        query: String
    ): CurrentWeatherEntity?
}