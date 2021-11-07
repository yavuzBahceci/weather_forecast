package mobile.app.android.myavuzbahceci.weatherforecast.business.repository

import mobile.app.android.myavuzbahceci.weatherforecast.BuildConfig
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.api.WeatherForecastApi
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.mapper.CurrentWeatherDtoMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.mapper.FullWeatherDtoMapper

class WeatherRepositoryImpl(
    private val weatherForecastApi: WeatherForecastApi,
    private val currentWeatherDtoMapper: CurrentWeatherDtoMapper,
    private val fullWeatherDtoMapper: FullWeatherDtoMapper
) : WeatherRepository {
    override suspend fun getCurrentWeatherWithCityName(cityName: String): CurrentWeather {
        return currentWeatherDtoMapper.mapToDomainModel(
            weatherForecastApi.getCurrentWeatherConditionsWithCityName(
                cityName,
                BuildConfig.API_KEY
            )
        )
    }

    override suspend fun getCurrentWeatherWithCoords(lon: Double, lat: Double): CurrentWeather {
        return currentWeatherDtoMapper.mapToDomainModel(
            weatherForecastApi.getCurrentWeatherWithCoords(lon, lat, BuildConfig.API_KEY), lon, lat
        )
    }

    override suspend fun getFullWeatherWithCityName(cityName: String): FullWeather {
        return fullWeatherDtoMapper.mapToDomainModel(
            weatherForecastApi.getFullWeatherWithCityName(cityName, BuildConfig.API_KEY)
        )
    }

    override suspend fun getFullWeatherWithCoords(lon: Double, lat: Double): FullWeather {
        return fullWeatherDtoMapper.mapToDomainModel(
            weatherForecastApi.getFullWeatherWithCoords(lon, lat, BuildConfig.API_KEY), lon, lat
        )
    }


}