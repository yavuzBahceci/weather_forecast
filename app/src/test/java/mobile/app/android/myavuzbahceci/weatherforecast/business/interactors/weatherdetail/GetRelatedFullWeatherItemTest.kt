package mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.weatherdetail

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.fullweather.GetFullWeatherByCityName
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepository
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepositoryImpl
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.FullWeatherDaoFake
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.database.WeatherDataBaseFake
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper.FullWeatherEntityMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.api.WeatherForecastApi
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.data.MockWeatherApiResponses
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.mapper.CurrentWeatherDtoMapper
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.network.mapper.FullWeatherDtoMapper
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetRelatedFullWeatherItemTest {

    private val appDatabase = WeatherDataBaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val DUMMY_APP_ID = "asdxhasaczxc" // doesn't matter
    private val DUMMY_QUERY = "Paris" // doesn't matter

    lateinit var gson: Gson

    // system in test
    private lateinit var getRelatedFullWeatherItem: GetRelatedFullWeatherItem
    private val LONGITUDE = 29.0038
    private val LATITUDE = 41.055
    private val CITY_NAME = "Şişli"
    private val DT = 1636275600L

    // Dependencies
    private lateinit var getFullWeatherByCityName: GetFullWeatherByCityName
    private lateinit var weatherForecastApi: WeatherForecastApi
    private lateinit var fakeFullWeatherDao: FullWeatherDaoFake
    private val dtoMapper = FullWeatherDtoMapper()
    private lateinit var entityMapper: FullWeatherEntityMapper
    private lateinit var weatherRepository: WeatherRepository

    @BeforeEach
    fun setup() {

        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/api/weather/")
        weatherForecastApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WeatherForecastApi::class.java)
        fakeFullWeatherDao = FullWeatherDaoFake(appDatabaseFake = appDatabase)
        weatherRepository = WeatherRepositoryImpl(
            weatherForecastApi,
            currentWeatherDtoMapper = CurrentWeatherDtoMapper(),
            fullWeatherDtoMapper = dtoMapper
        )
        gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create()
        entityMapper = FullWeatherEntityMapper(gson = gson)
        getFullWeatherByCityName = GetFullWeatherByCityName(
            gson,
            weatherRepository,
            fakeFullWeatherDao,
            fullWeatherEntityMapper = entityMapper
        )

        // instantiate the system in test
        getRelatedFullWeatherItem = GetRelatedFullWeatherItem(
            gson,
            weatherRepository,
            fakeFullWeatherDao,
            entityMapper,
        )
    }

    /**
     * 1. Get full weather from the network and insert into cache
     * 2. Try to retrieve weather by its specific date time
     */
    @Test
    fun getRelatedFullWeatherItemAddToDB(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWeatherApiResponses.sixteenDaysFromResponse)
        )

        // confirm the cache is empty to start
        assert(fakeFullWeatherDao.getFullWeatherWithCityName(CITY_NAME) == null)

        // get full weather from network and insert into cache
        val networkResult = getFullWeatherByCityName.execute(CITY_NAME, true).toList()

        // confirm the cache is no longer empty
        assert(
            fakeFullWeatherDao.getFullWeatherWithCoords(
                CurrentWeather.Coord(LONGITUDE, LATITUDE).serialize()
            ) != null
        )

        // run use case
        val weatherAsFlow =
            getRelatedFullWeatherItem.execute(CITY_NAME, LONGITUDE, LATITUDE, DT, true).toList()

        // first emission should be `loading`
        assert(weatherAsFlow[0].loading)

        val weather =
            fakeFullWeatherDao.getFullWeatherWithCoords(
                CurrentWeather.Coord(LONGITUDE, LATITUDE).serialize()
            )?.dailySerialized?.deserializeDaily()
                ?.get(0)

        // confirm it is proper date time
        assert(weather?.dt == DT)
        // confirm it is actually a Daily object
        assert(weather is FullWeather.Daily)

        // 'loading' should be false now
        assert(!weatherAsFlow[1].loading)
    }

    fun <E> E.serialize(): String {
        return gson.toJson(this)
    }

    private fun String.deserializeDaily(): List<FullWeather.Daily> {
        return gson.fromJson(
            this,
            object : TypeToken<ArrayList<FullWeather.Daily>>() {}.type
        )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}