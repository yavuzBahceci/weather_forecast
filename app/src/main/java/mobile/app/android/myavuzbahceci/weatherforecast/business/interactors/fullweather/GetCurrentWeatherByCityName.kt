package mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.fullweather

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.data.DataState
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepository
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.CurrentWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper.CurrentWeatherEntityMapper

class GetCurrentWeatherByCityName(
    val gson: Gson,
    val weatherRepository: WeatherRepository,
    val currentWeatherDao: CurrentWeatherDao,
    val currentWeatherEntityMapper: CurrentWeatherEntityMapper
) {

    fun execute(
        cityName: String,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<CurrentWeather>> = flow {
            try {
                emit(DataState.loading())

                var currentWeather = getCurrentWeatherFromCache(cityName)
                if (currentWeather != null && !isNetworkAvailable) {
                    emit(DataState.success(currentWeather))
                } else {
                    if (isNetworkAvailable) {
                        val networkCurrentWeather =
                            getCurrentWeatherFromNetwork(cityName)

                        currentWeatherDao.insertCurrentWeather(
                            currentWeatherEntityMapper.mapFromDomainModel(networkCurrentWeather)
                        )
                    }
                    currentWeather = getCurrentWeatherFromCache(cityName)

                    if (currentWeather != null) {
                        emit(DataState.success(currentWeather))
                    } else {
                        throw Exception("Unable to get current weather from the cache.")
                    }
                }
            }catch (e: Exception){
                emit(DataState.error<CurrentWeather>(e.message ?: "Unknown Error"))
            }

    }

    private suspend fun getCurrentWeatherFromCache(cityName: String): CurrentWeather? {
        currentWeatherDao.getCurrentWeatherWithCityName(cityName)?.let { currentWeatherEntity ->
            return currentWeatherEntityMapper.mapToDomainModel(currentWeatherEntity)
        }
        currentWeatherDao.searchCities(cityName)?.let { currentWeatherEntity ->
            return currentWeatherEntityMapper.mapToDomainModel(currentWeatherEntity)
        }
        return null
    }

    private suspend fun getCurrentWeatherFromNetwork(cityName: String): CurrentWeather {
        return weatherRepository.getCurrentWeatherWithCityName(cityName)
    }
}