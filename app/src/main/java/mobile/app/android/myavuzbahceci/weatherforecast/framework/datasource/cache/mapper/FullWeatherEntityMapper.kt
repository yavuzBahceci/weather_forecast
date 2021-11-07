package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather.Coord
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.util.DomainMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.model.FullWeatherEntity
import javax.inject.Inject

class FullWeatherEntityMapper
@Inject
constructor(
    private val gson: Gson
) : DomainMapper<FullWeatherEntity, FullWeather> {
    override fun mapToDomainModel(model: FullWeatherEntity): FullWeather {
        return FullWeather(
            model.dailySerialized.deserializeDaily(),
            model.citySerialized.deserializeCity()
        )
    }

    override fun mapFromDomainModel(domainModel: FullWeather): FullWeatherEntity {

        return FullWeatherEntity(
            Coord(domainModel.city.coord.lon, domainModel.city.coord.lat).serialize(),
            domainModel.city.cityName,
            domainModel.city.cityId,
            domainModel.city.timezone,
            domainModel.daily.serialize(),
            domainModel.city.serialize()
        )
    }

    private fun <E> E.serialize(): String{
        return gson.toJson(this)
    }

    private fun String.deserializeDaily(): List<FullWeather.Daily> {
        return gson.fromJson(
            this,
            object : TypeToken<ArrayList<FullWeather.Daily>>() {}.type
        )
    }

    private fun String.deserializeCity(): FullWeather.City {
        return gson.fromJson(
            this,
            object : TypeToken<FullWeather.City>() {}.type
        )
    }
}