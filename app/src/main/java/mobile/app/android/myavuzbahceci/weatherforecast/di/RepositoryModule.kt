package mobile.app.android.myavuzbahceci.weatherforecast.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepository
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepositoryImpl
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.api.WeatherForecastApi
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.mapper.CurrentWeatherDtoMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.mapper.FullWeatherDtoMapper
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherForecastApi: WeatherForecastApi,
        fullWeatherDtoMapper: FullWeatherDtoMapper,
        currentWeatherDtoMapper: CurrentWeatherDtoMapper
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            weatherForecastApi = weatherForecastApi,
            fullWeatherDtoMapper = fullWeatherDtoMapper,
            currentWeatherDtoMapper = currentWeatherDtoMapper
        )
    }
}