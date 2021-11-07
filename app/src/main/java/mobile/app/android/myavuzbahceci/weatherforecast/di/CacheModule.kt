package mobile.app.android.myavuzbahceci.weatherforecast.di

import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.CurrentWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.FullWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.database.WeatherForecastDatabase
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper.CurrentWeatherEntityMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper.FullWeatherEntityMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.BaseApplication
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create()
    }

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): WeatherForecastDatabase {
        return Room
            .databaseBuilder(
                app,
                WeatherForecastDatabase::class.java,
                WeatherForecastDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideFullWeatherDao(db: WeatherForecastDatabase): FullWeatherDao {
        return db.fullWeatherDao()
    }

    @Singleton
    @Provides
    fun provideCurrentWeatherDao(db: WeatherForecastDatabase): CurrentWeatherDao {
        return db.currentWeatherDao()
    }

    @Singleton
    @Provides
    fun provideFullWeatherCacheMapper(
        gson: Gson
    ): FullWeatherEntityMapper {
        return FullWeatherEntityMapper(gson)
    }

    @Singleton
    @Provides
    fun provideCurrentWeatherCacheMapper(
        gson: Gson
    ): CurrentWeatherEntityMapper {
        return CurrentWeatherEntityMapper(gson)
    }

}