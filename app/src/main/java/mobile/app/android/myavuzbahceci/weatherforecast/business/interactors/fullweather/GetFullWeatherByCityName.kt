package mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.fullweather

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.data.DataState
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepository
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.FullWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper.FullWeatherEntityMapper

class GetFullWeatherByCityName(
    val gson: Gson,
    private val weatherRepository: WeatherRepository,
    private val fullWeatherDao: FullWeatherDao,
    private val fullWeatherEntityMapper: FullWeatherEntityMapper
) {
    fun execute(
        cityName: String,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<FullWeather>> = flow {
        try {
            emit(DataState.loading())

            var fullWeather = getFullWeatherFromCache(cityName)
            if (fullWeather != null && !isNetworkAvailable) {
                emit(DataState.success(fullWeather))
            } else {
                if (isNetworkAvailable) {
                    val networkCurrentWeather =
                        getFullWeatherFromNetwork(cityName)

                    fullWeatherDao.insertFullWeather(
                        fullWeatherEntityMapper.mapFromDomainModel(networkCurrentWeather)
                    )
                }
                fullWeather = getFullWeatherFromCache(cityName)

                if (fullWeather != null) {
                    emit(DataState.success(fullWeather))
                } else {
                    throw Exception("Unable to get current weather from the cache.")
                }
            }

        } catch (e: Exception) {
            emit(DataState.error<FullWeather>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getFullWeatherFromCache(cityName: String): FullWeather? {
        return fullWeatherDao.getFullWeatherWithCityName(cityName)?.let { fullWeatherEntity ->
            fullWeatherEntityMapper.mapToDomainModel(fullWeatherEntity)
        }
    }

    private suspend fun getFullWeatherFromNetwork(cityName: String): FullWeather {
        return weatherRepository.getFullWeatherWithCityName(cityName)
    }
}