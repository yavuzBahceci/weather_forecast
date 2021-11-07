package mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.weatherdetail

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.data.DataState
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather.Daily
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepository
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.FullWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper.FullWeatherEntityMapper

class GetRelatedFullWeatherItem(
    val gson: Gson,
    private val weatherRepository: WeatherRepository,
    private val fullWeatherDao: FullWeatherDao,
    private val fullWeatherEntityMapper: FullWeatherEntityMapper
) {

    fun execute(
        cityName: String?,
        lon: Double?,
        lat: Double?,
        dt: Long,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<Daily>> = flow {
        try {
            emit(DataState.loading())

            when {
                (lon == null || lat == null) && cityName != null -> {
                    var fullWeather = getFullWeatherByCityNameFromCache(cityName)
                    if (fullWeather != null && !isNetworkAvailable) {
                        emit(DataState.success(fullWeather.daily.first { it.dt == dt }))
                    } else {
                        if (isNetworkAvailable) {
                            val networkCurrentWeather =
                                getFullWeatherByCityNameFromNetwork(cityName)

                            fullWeatherDao.insertFullWeather(
                                fullWeatherEntityMapper.mapFromDomainModel(networkCurrentWeather)
                            )
                        }
                        fullWeather = getFullWeatherByCityNameFromCache(cityName)

                        if (fullWeather != null) {
                            emit(DataState.success(fullWeather.daily.first { it.dt == dt }))
                        } else {
                            throw Exception("Unable to get current weather from the cache.")
                        }
                    }
                }
                lon != null && lat != null -> {
                    var fullWeather = getFullWeatherByCoordsFromCache(lon, lat)
                    if (fullWeather != null && !isNetworkAvailable) {
                        emit(DataState.success(fullWeather.daily.first { it.dt == dt }))
                    } else {
                        if (isNetworkAvailable) {
                            val networkCurrentWeather =
                                getFullWeatherByCoordsFromNetwork(lon, lat)

                            fullWeatherDao.insertFullWeather(
                                fullWeatherEntityMapper.mapFromDomainModel(networkCurrentWeather)
                            )
                        }
                        fullWeather = getFullWeatherByCoordsFromCache(lon, lat)

                        if (fullWeather != null) {
                            emit(DataState.success(fullWeather.daily.first { it.dt == dt }))
                        } else {
                            throw Exception("Unable to get current weather from the cache.")
                        }
                    }
                }
            }


        } catch (e: Exception) {
            emit(DataState.error<FullWeather.Daily>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getFullWeatherByCityNameFromCache(cityName: String): FullWeather? {
        return fullWeatherDao.getFullWeatherWithCityName(cityName)?.let { fullWeatherEntity ->
            fullWeatherEntityMapper.mapToDomainModel(fullWeatherEntity)
        }
    }

    private suspend fun getFullWeatherByCityNameFromNetwork(cityName: String): FullWeather {
        return weatherRepository.getFullWeatherWithCityName(cityName)
    }

    private suspend fun getFullWeatherByCoordsFromCache(lon: Double, lat: Double): FullWeather? {
        return fullWeatherDao.getFullWeatherWithCoords(
            CurrentWeather.Coord(lon, lat).serialize()
        )?.let { fullWeatherEntity ->
            fullWeatherEntityMapper.mapToDomainModel(fullWeatherEntity)
        }
    }

    private suspend fun getFullWeatherByCoordsFromNetwork(lon: Double, lat: Double): FullWeather {
        return weatherRepository.getFullWeatherWithCoords(lon, lat)
    }

    fun <E> E.serialize(): String {
        return gson.toJson(this)
    }
}