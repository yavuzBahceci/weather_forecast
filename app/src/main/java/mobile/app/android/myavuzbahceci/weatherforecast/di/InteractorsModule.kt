package mobile.app.android.myavuzbahceci.weatherforecast.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.fullweather.*
import mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.weatherdetail.GetRelatedFullWeatherItem
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepository
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.CurrentWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.FullWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper.CurrentWeatherEntityMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper.FullWeatherEntityMapper

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideGetCurrentLocation(
        @ApplicationContext context: Context
    ): GetCurrentLocation {
        return GetCurrentLocation(context)
    }



    @ViewModelScoped
    @Provides
    fun provideGetCurrentWeatherByCityName(
        gson: Gson,
        weatherRepository: WeatherRepository,
        currentWeatherDao: CurrentWeatherDao,
        currentWeatherEntityMapper: CurrentWeatherEntityMapper
    ): GetCurrentWeatherByCityName {
        return GetCurrentWeatherByCityName(
            gson, weatherRepository, currentWeatherDao, currentWeatherEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetCurrentWeatherByCoords(
        gson: Gson,
        weatherRepository: WeatherRepository,
        currentWeatherDao: CurrentWeatherDao,
        currentWeatherEntityMapper: CurrentWeatherEntityMapper
    ): GetCurrentWeatherByCoords {
        return GetCurrentWeatherByCoords(
            gson, weatherRepository, currentWeatherDao, currentWeatherEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetFullWeatherByCityName(
        gson: Gson,
        weatherRepository: WeatherRepository,
        fullWeatherDao: FullWeatherDao,
        fullWeatherEntityMapper: FullWeatherEntityMapper
    ): GetFullWeatherByCityName {
        return GetFullWeatherByCityName(
            gson, weatherRepository, fullWeatherDao, fullWeatherEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetFullWeatherByCoords(
        gson: Gson,
        weatherRepository: WeatherRepository,
        fullWeatherDao: FullWeatherDao,
        fullWeatherEntityMapper: FullWeatherEntityMapper
    ): GetFullWeatherWithCoords {
        return GetFullWeatherWithCoords(
            gson, weatherRepository, fullWeatherDao, fullWeatherEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetRelatedFullWeatherItem(
        gson: Gson,
        weatherRepository: WeatherRepository,
        fullWeatherDao: FullWeatherDao,
        fullWeatherEntityMapper: FullWeatherEntityMapper
    ): GetRelatedFullWeatherItem {
        return GetRelatedFullWeatherItem(
            gson, weatherRepository, fullWeatherDao, fullWeatherEntityMapper
        )
    }

}

