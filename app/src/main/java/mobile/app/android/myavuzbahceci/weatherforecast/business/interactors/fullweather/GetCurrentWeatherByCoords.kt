package mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.fullweather

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.data.DataState
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepository
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.CurrentWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper.CurrentWeatherEntityMapper

class GetCurrentWeatherByCoords(
    val gson: Gson,
    private val weatherRepository: WeatherRepository,
    private val currentWeatherDao: CurrentWeatherDao,
    private val currentWeatherEntityMapper: CurrentWeatherEntityMapper
) {

    fun execute(
        lon: Double,
        lat: Double,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<CurrentWeather>> = flow {

        try {
            emit(DataState.loading())
            var currentWeather = getCurrentWeatherFromCache(lon, lat)
            if (currentWeather != null && !isNetworkAvailable) {
                emit(DataState.success(currentWeather))
            } else {
                if (isNetworkAvailable) {
                    val networkCurrentWeather =
                        getCurrentWeatherFromNetwork(lon, lat)

                    currentWeatherDao.insertCurrentWeather(
                        currentWeatherEntityMapper.mapFromDomainModel(networkCurrentWeather)
                    )
                    println("Network Current Response: City:  ${networkCurrentWeather.coords}")
                    println(
                        "Insert Current Cache: ${
                            currentWeatherEntityMapper.mapFromDomainModel(
                                networkCurrentWeather
                            )
                        }"
                    )

                }
                currentWeather = getCurrentWeatherFromCache(lon, lat)

                if (currentWeather != null) {
                    emit(DataState.success(currentWeather))
                } else {
                    throw Exception("Unable to get current weather from the cache.")
                }
            }
        } catch (e: Exception) {
            emit(DataState.error<CurrentWeather>(e.message ?: "Unknown Error"))
        }

    }

    private suspend fun getCurrentWeatherFromCache(lon: Double, lat: Double): CurrentWeather? {
        println("Query for search: ${CurrentWeather.Coord(lon, lat).serialize()}")
        return currentWeatherDao.getCurrentWeatherWithCoords(
            CurrentWeather.Coord(lon, lat).serialize()
        )?.let { currentWeatherEntity ->
            currentWeatherEntityMapper.mapToDomainModel(currentWeatherEntity)
        }
    }

    private suspend fun getCurrentWeatherFromNetwork(lon: Double, lat: Double): CurrentWeather {
        return weatherRepository.getCurrentWeatherWithCoords(lon, lat)
    }

    private fun <E> E.serialize(): String {
        return gson.toJson(this)
    }
}