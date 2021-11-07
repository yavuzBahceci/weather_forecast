package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.mapper

import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather.City
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather.Daily
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather.Daily.FeelsLike
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather.Daily.Temp
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.util.DomainMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.FullWeatherResponse
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.common.WeatherDto
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.currentweather.CoordDto
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.fullweather.CityDto
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.fullweather.DailyDto
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.fullweather.FeelsLikeDto
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.response.dto.fullweather.TempDto

class FullWeatherDtoMapper : DomainMapper<FullWeatherResponse, FullWeather> {
    override fun mapToDomainModel(model: FullWeatherResponse): FullWeather {
        return FullWeather(
            model.daily.toDailyDomain(),
            model.cityDto.toCityDomain(),
            model.cod,
            model.message,
            model.count
        )
    }

    fun mapToDomainModel(model: FullWeatherResponse, lon: Double, lat: Double): FullWeather {
        return FullWeather(
            model.daily.toDailyDomain(),
            model.cityDto.toCityDomain(lon, lat),
            model.cod,
            model.message,
            model.count
        )
    }

    override fun mapFromDomainModel(domainModel: FullWeather): FullWeatherResponse {
        return FullWeatherResponse(
            domainModel.city.toCityDto(),
            domainModel.daily.toDailyDto(),
            domainModel.cod ?: 200,
            domainModel.message ?: 0.0,
            domainModel.count ?: 16
        )
    }
}


private fun City.toCityDto(): CityDto {
    return CityDto(
        this.cityId,
        this.cityName,
        CoordDto(this.coord.lon, this.coord.lat),
        this.country,
        this.population.toInt(),
        this.timezone.toDouble()
    )
}

private fun CityDto.toCityDomain(): City {
    return City(
        this.cityId,
        this.cityName,
        CurrentWeather.Coord(this.coord.lon, this.coord.lat),
        this.country,
        this.population.toLong(),
        this.timezone.toLong()
    )
}

private fun CityDto.toCityDomain(lon: Double, lat: Double): City {
    return City(
        this.cityId,
        this.cityName,
        CurrentWeather.Coord(lon, lat),
        this.country,
        this.population.toLong(),
        this.timezone.toLong()
    )
}

private fun List<Daily>.toDailyDto(): List<DailyDto> {
    val newList = arrayListOf<DailyDto>()
    this.map {
        newList.add(
            DailyDto(
                it.dt,
                it.sunrise,
                it.sunset,
                it.moonrise,
                it.moonset,
                it.moonPhase,
                TempDto(
                    it.temp.day,
                    it.temp.min,
                    it.temp.max,
                    it.temp.night,
                    it.temp.eve,
                    it.temp.morn
                ),
                FeelsLikeDto(
                    it.feelsLike.day,
                    it.feelsLike.night,
                    it.feelsLike.eve,
                    it.feelsLike.morn
                ),
                it.pressure,
                it.humidity,
                it.dewPoint,
                it.windSpeed,
                it.windDeg,
                it.windGust,
                it.weather.toWeatherItemDto(),
                it.clouds,
                it.pop,
                it.uvi,
                it.rain
            )
        )
    }
    return newList
}

private fun List<Daily.Weather>.toWeatherItemDto(): List<WeatherDto> {
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

private fun List<DailyDto>.toDailyDomain(): List<Daily> {
    val newList = arrayListOf<Daily>()
    this.map {
        newList.add(
            Daily(
                it.dt,
                it.sunrise,
                it.sunset,
                it.moonrise,
                it.moonset,
                it.moonPhase,
                Temp(
                    it.tempDto.day,
                    it.tempDto.min,
                    it.tempDto.max,
                    it.tempDto.night,
                    it.tempDto.eve,
                    it.tempDto.morn
                ),
                FeelsLike(
                    it.feelsLikeDto.day,
                    it.feelsLikeDto.night,
                    it.feelsLikeDto.eve,
                    it.feelsLikeDto.morn
                ),
                it.pressure,
                it.humidity,
                it.dewPoint,
                it.windSpeed,
                it.windDeg,
                it.windGust,
                it.weatherDto.toWeatherItemDomain(),
                it.clouds,
                it.pop,
                it.uvi,
                it.rain
            )
        )
    }
    return newList
}

private fun List<WeatherDto>.toWeatherItemDomain(): List<Daily.Weather> {
    val newList = arrayListOf<Daily.Weather>()
    this.map {
        newList.add(
            Daily.Weather(
                it.id,
                it.main,
                it.description,
                it.icon
            )
        )
    }
    return newList
}
