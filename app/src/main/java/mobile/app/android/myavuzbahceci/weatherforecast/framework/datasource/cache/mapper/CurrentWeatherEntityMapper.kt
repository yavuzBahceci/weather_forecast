package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.util.DomainMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model.CurrentWeatherEntity
import javax.inject.Inject

class CurrentWeatherEntityMapper
@Inject
constructor(
    private val gson: Gson
) : DomainMapper<CurrentWeatherEntity, CurrentWeather> {
    override fun mapToDomainModel(model: CurrentWeatherEntity): CurrentWeather {
        return CurrentWeather(
            model.coordSerialized.deserializeCoords(),
            model.weatherSerialized.deserializeWeatherList(),
            model.base,
            model.mainSerialized.deserializeMain(),
            model.visibility,
            model.windSerialized.deserializeWind(),
            model.cloudsSerialized.deserializeClouds(),
            model.dt,
            model.sysSerialized.deserializeSys(),
            model.timezone,
            model.city_id,
            model.cityName,
            model.cod
        )
    }

    override fun mapFromDomainModel(domainModel: CurrentWeather): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            domainModel.coords.serialize(),
            domainModel.cityId,
            domainModel.cityName,
            domainModel.weather.serialize(),
            domainModel.base,
            domainModel.main.serialize(),
            domainModel.visibility,
            domainModel.wind.serialize(),
            domainModel.clouds.serialize(),
            domainModel.dt,
            domainModel.sys.serialize(),
            domainModel.timezone,
            domainModel.cod
        )
    }

    private fun <E> E.serialize(): String{
        return gson.toJson(this)
    }

    private fun String.deserializeCoords(): CurrentWeather.Coord {
        return gson.fromJson(
            this,
            object : TypeToken<CurrentWeather.Coord>() {}.type
        )
    }

    private fun String.deserializeMain(): CurrentWeather.Main {
        return gson.fromJson(
            this,
            object : TypeToken<CurrentWeather.Main>() {}.type
        )
    }

    private fun String.deserializeSys(): CurrentWeather.Sys {
        return gson.fromJson(
            this,
            object : TypeToken<CurrentWeather.Sys>() {}.type
        )
    }

    private fun String.deserializeClouds(): CurrentWeather.Clouds {
        return gson.fromJson(
            this,
            object : TypeToken<CurrentWeather.Clouds>() {}.type
        )
    }

    private fun String.deserializeWind(): CurrentWeather.Wind {
        return gson.fromJson(
            this,
            object : TypeToken<CurrentWeather.Wind>() {}.type
        )
    }

    private fun String.deserializeWeatherList(): List<CurrentWeather.Weather> {
        return gson.fromJson(
            this,
            object : TypeToken<ArrayList<CurrentWeather.Weather>>() {}.type
        )
    }
}












