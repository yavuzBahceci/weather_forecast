package mobile.app.android.myavuzbahceci.weatherforecast.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobile.app.android.myavuzbahceci.weatherforecast.BuildConfig
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.api.WeatherForecastApi
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.mapper.CurrentWeatherDtoMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.mapper.FullWeatherDtoMapper
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideFullWeatherDtoMapper(): FullWeatherDtoMapper {
        return FullWeatherDtoMapper()
    }

    @Singleton
    @Provides
    fun provideCurrentWeatherDtoMapper(): CurrentWeatherDtoMapper {
        return CurrentWeatherDtoMapper()
    }

    @Singleton
    @Provides
    fun provideOkHttpClientNoAuth(): OkHttpClient {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_NONE)
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .readTimeout(6, TimeUnit.SECONDS)
            .connectTimeout(6, TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .cache(null)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherService(
        okHttpClient: OkHttpClient
    ): WeatherForecastApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WeatherForecastApi::class.java)
    }


}