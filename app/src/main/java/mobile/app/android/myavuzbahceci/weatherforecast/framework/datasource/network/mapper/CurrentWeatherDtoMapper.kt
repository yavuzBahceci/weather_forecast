package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.mapper

import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather.*
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.util.DomainMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.CurrentWeatherResponse
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.common.WeatherDto
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.currentweather.*

class CurrentWeatherDtoMapper : DomainMapper<CurrentWeatherResponse, CurrentWeather> {
    override fun mapToDomainModel(model: CurrentWeatherResponse): CurrentWeather {
        return CurrentWeather(
            Coord(
                model.coordDto.lon,
                model.coordDto.lat
            ),
            model.weatherDto.toWeatherDomain(),
            model.base,
            Main(
                model.mainDto.temp,
                model.mainDto.feelsLike,
                model.mainDto.tempMin,
                model.mainDto.tempMax,
                model.mainDto.pressure,
                model.mainDto.humidity
            ),
            model.visibility,
            Wind(
                model.windDto.speed,
                model.windDto.deg,
                model.windDto.gust
            ),
            Clouds(
                model.cloudsDto.all
            ),
            model.dt,
            Sys(
                model.sysDto.type,
                model.sysDto.id,
                model.sysDto.message,
                model.sysDto.country,
                model.sysDto.sunrise,
                model.sysDto.sunset
            ),
            model.timezone,
            model.cityId,
            model.cityName,
            model.cod
        )
    }

    fun mapToDomainModel(model: CurrentWeatherResponse, lon: Double, lat: Double): CurrentWeather {
        return CurrentWeather(
            Coord(lon,lat),
            model.weatherDto.toWeatherDomain(),
            model.base,
            Main(
                model.mainDto.temp,
                model.mainDto.feelsLike,
                model.mainDto.tempMin,
                model.mainDto.tempMax,
                model.mainDto.pressure,
                model.mainDto.humidity
            ),
            model.visibility,
            Wind(
                model.windDto.speed,
                model.windDto.deg,
                model.windDto.gust
            ),
            Clouds(
                model.cloudsDto.all
            ),
            model.dt,
            Sys(
                model.sysDto.type,
                model.sysDto.id,
                model.sysDto.message,
                model.sysDto.country,
                model.sysDto.sunrise,
                model.sysDto.sunset
            ),
            model.timezone,
            model.cityId,
            model.cityName,
            model.cod
        )
    }

    override fun mapFromDomainModel(domainModel: CurrentWeather): CurrentWeatherResponse {
        return CurrentWeatherResponse(
            CoordDto(
                domainModel.coords.lon,
                domainModel.coords.lat
            ),
            domainModel.weather.toWeatherDto(),
            domainModel.base,
            MainDto(
                domainModel.main.temp,
                domainModel.main.feelsLike,
                domainModel.main.tempMin,
                domainModel.main.tempMax,
                domainModel.main.pressure,
                domainModel.main.humidity
            ),
            domainModel.visibility,
            WindDto(
                domainModel.wind.speed,
                domainModel.wind.deg,
                domainModel.wind.gust
            ),
            CloudsDto(
                domainModel.clouds.all
            ),
            domainModel.dt,
            SysDto(
                domainModel.sys.type,
                domainModel.sys.id,
                domainModel.sys.message,
                domainModel.sys.country,
                domainModel.sys.sunrise,
                domainModel.sys.sunset
            ),
            domainModel.timezone,
            domainModel.cityId,
            domainModel.cityName,
            domainModel.cod
        )
    }
}

private fun List<Weather>.toWeatherDto(): List<WeatherDto> {
    val newList = arrayListOf<WeatherDto>()
    this.map {
        newList.add(
            WeatherDto(
                it.id,
                it.main,
                it.description,
                it.icon
            )
        )
    }
    return newList
}


private fun List<WeatherDto>.toWeatherDomain(): List<Weather> {
    val newList = arrayListOf<Weather>()
    this.map {
        newList.add(
            Weather(
                it.id,
                it.main,
                it.description,
                it.icon
            )
        )
    }
    return newList
}
