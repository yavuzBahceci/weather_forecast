package mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.fullweather

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.data.DataState
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepository
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.FullWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper.FullWeatherEntityMapper

class GetFullWeatherWithCoords(
    val gson: Gson,
    private val weatherRepository: WeatherRepository,
    private val fullWeatherDao: FullWeatherDao,
    private val fullWeatherEntityMapper: FullWeatherEntityMapper
) {

    fun execute(
        lon: Double,
        lat: Double,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<FullWeather>> = flow {

        try {
            emit(DataState.loading())
            var fullWeather = getFullWeatherFromCache(lon, lat)
            if (fullWeather != null && !isNetworkAvailable) {
                emit(DataState.success(fullWeather))
            } else {
                if (isNetworkAvailable) {
                    val networkFullWeather =
                        getFullWeatherFromNetwork(lon, lat)
                    fullWeatherDao.insertFullWeather(
                        fullWeatherEntityMapper.mapFromDomainModel(networkFullWeather)
                    )
                    println("Network Response: City:  ${networkFullWeather.city}")
                    println(
                        "Insert Cache: ${
                            fullWeatherEntityMapper.mapFromDomainModel(
                                networkFullWeather
                            )
                        }"
                    )

                }
                fullWeather = getFullWeatherFromCache(lon, lat)

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

    private suspend fun getFullWeatherFromCache(lon: Double, lat: Double): FullWeather? {
        println("Query for search: ${CurrentWeather.Coord(lon, lat).serialize()}")
        return fullWeatherDao.getFullWeatherWithCoords(
            CurrentWeather.Coord(lon, lat).serialize()
        )?.let { fullWeatherEntity ->
            fullWeatherEntityMapper.mapToDomainModel(fullWeatherEntity)
        }
    }

    private suspend fun getFullWeatherFromNetwork(lon: Double, lat: Double): FullWeather {
        return weatherRepository.getFullWeatherWithCoords(lon, lat)
    }

    fun <E> E.serialize(): String {
        return gson.toJson(this)
    }
}