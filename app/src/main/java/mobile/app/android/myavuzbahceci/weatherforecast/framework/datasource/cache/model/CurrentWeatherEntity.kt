package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(

    @PrimaryKey
    @ColumnInfo(name = "coord")
    val coordSerialized: String,

    @ColumnInfo(name = "id")
    val city_id: Int,

    @ColumnInfo(name = "city_name")
    val cityName: String,

    @ColumnInfo(name = "weather")
    val weatherSerialized: String,

    @ColumnInfo(name = "base")
    val base: String,

    @ColumnInfo(name = "main")
    val mainSerialized: String,

    @ColumnInfo(name = "visibility")
    val visibility: Int,

    @ColumnInfo(name = "wind")
    val windSerialized: String,

    @ColumnInfo(name = "clouds")
    val cloudsSerialized: String,

    @ColumnInfo(name = "dt")
    val dt: Long,

    @ColumnInfo(name = "sys")
    val sysSerialized: String,

    @ColumnInfo(name = "timezone")
    val timezone: Long,

    @ColumnInfo(name = "cod")
    val cod: Int
)