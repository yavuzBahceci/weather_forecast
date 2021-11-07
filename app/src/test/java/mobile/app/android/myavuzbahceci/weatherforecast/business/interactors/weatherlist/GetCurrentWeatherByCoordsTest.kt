package mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.weatherlist

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.fullweather.GetCurrentWeatherByCoords
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepository
import mobile.app.android.myavuzbahceci.weatherforecast.business.repository.WeatherRepositoryImpl
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.CurrentWeatherDao
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.dao.CurrentWeatherDaoFake
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.database.WeatherDataBaseFake
import mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.cache.mapper.CurrentWeatherEntityMapper
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

class GetCurrentWeatherByCoordsTest {

    private val appDatabase = WeatherDataBaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    lateinit var gson: Gson

    // system in test
    private lateinit var getCurrentWeatherByCoords: GetCurrentWeatherByCoords
    private val LONGITUDE = 29.0038
    private val LATITUDE = 41.055
    private val CITY_NAME = "Şişli"
    private val DT = 1636275600L
    private val coords = CurrentWeather.Coord(LONGITUDE, LATITUDE)
    private var coordsSerialized = ""

    // Dependencies
    private lateinit var weatherForecastApi: WeatherForecastApi
    private lateinit var fakeCurrentWeatherDao: CurrentWeatherDao
    private val dtoMapper = FullWeatherDtoMapper()
    private lateinit var entityMapper: CurrentWeatherEntityMapper
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
        fakeCurrentWeatherDao = CurrentWeatherDaoFake(appDatabaseFake = appDatabase)
        weatherRepository = WeatherRepositoryImpl(
            weatherForecastApi,
            currentWeatherDtoMapper = CurrentWeatherDtoMapper(),
            fullWeatherDtoMapper = dtoMapper
        )
        gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create()
        coordsSerialized = coords.serialize()
        entityMapper = CurrentWeatherEntityMapper(gson = gson)

        // instantiate the system in test
        getCurrentWeatherByCoords = GetCurrentWeatherByCoords(
            gson,
            weatherRepository,
            fakeCurrentWeatherDao,
            entityMapper
        )
    }

    /*
    * 1. Are the current weather retrieved from the network?
    * 2. Are the current weather inserted into the cache?
    * 3. Are the current weather then emitted as a flow from the cache?
    */
    @Test
    fun getCurrentWeatherInsertCache(): Unit = runBlocking {

        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWeatherApiResponses.currentweatherFromResponse)
        )

        // confirm the cache is empty to start
        assert(fakeCurrentWeatherDao.getCurrentWeatherWithCoords(coordsSerialized) == null)

        val flowItems = getCurrentWeatherByCoords.execute(LONGITUDE, LATITUDE, true).toList()

        // confirm the cache is no longer empty
        assert(fakeCurrentWeatherDao.getCurrentWeatherWithCoords(coordsSerialized) != null)

        // first emission should be `loading`
        assert(flowItems[0].loading)

        val currentWeather = flowItems[1].data
        assert(currentWeather?.weather?.size ?: 0 > 0)

        assert(currentWeather is CurrentWeather)

        assert(!flowItems[1].loading) // loading should be false now
    }


    // Simulate Bad Request
    @Test
    fun getCurrentWeatherEmitHttpError(): Unit = runBlocking {

        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = getCurrentWeatherByCoords.execute(LONGITUDE, LATITUDE, true).toList()

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // Second emission should be the exception
        val error = flowItems[1].error
        assert(error != null)

        assert(!flowItems[1].loading) // loading should be false now
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    fun <E> E.serialize(): String {
        return gson.toJson(this)
    }
}
