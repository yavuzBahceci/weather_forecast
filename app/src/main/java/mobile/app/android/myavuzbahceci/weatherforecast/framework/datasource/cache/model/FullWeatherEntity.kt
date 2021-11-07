package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "full_weather")
data class FullWeatherEntity (

    @PrimaryKey
    @ColumnInfo(name = "coords")
    val coordsSerialized: String,

    @ColumnInfo(name = "city_name")
    val cityName: String,

    @ColumnInfo(name = "city_id")
    val cityId: Long,

    @ColumnInfo(name = "timezone")
    val timezone: Long,

    @ColumnInfo(name = "daily")
    val dailySerialized: String,

    @ColumnInfo(name = "city")
    val citySerialized: String,

)