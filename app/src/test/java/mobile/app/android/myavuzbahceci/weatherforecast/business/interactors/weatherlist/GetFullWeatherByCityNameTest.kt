import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
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

class GetFullWeatherByCityNameTest {

    private val appDatabase = WeatherDataBaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    lateinit var gson: Gson

    // system in test
    private lateinit var getFullWeatherByCityName: GetFullWeatherByCityName
    private val CITY_NAME = "Şişli"

    // Dependencies
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

        // instantiate the system in test
        getFullWeatherByCityName = GetFullWeatherByCityName(
            gson,
            weatherRepository,
            fakeFullWeatherDao,
            entityMapper
        )
    }

    /*
    * 1. Are the full weather retrieved from the network?
    * 2. Are the full weather inserted into the cache?
    * 3. Are the full weather then emitted as a flow from the cache?
    */
    @Test
    fun getFullWeatherWithCityNameInsertCache(): Unit = runBlocking {

        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWeatherApiResponses.sixteenDaysFromResponse)
        )

        // confirm the cache is empty to start
        assert(fakeFullWeatherDao.getFullWeatherWithCityName(CITY_NAME) == null)

        val flowItems = getFullWeatherByCityName.execute(CITY_NAME, true).toList()

        // confirm the cache is no longer empty
        assert(fakeFullWeatherDao.getFullWeatherWithCityName(CITY_NAME) != null)

        // first emission should be `loading`
        assert(flowItems[0].loading)

        val fullWeather = flowItems[1].data
        assert(fullWeather?.daily?.size ?: 0 > 0)

        assert(fullWeather is FullWeather)

        assert(!flowItems[1].loading) // loading should be false now
    }


    // Simulate Bad Request
    @Test
    fun getFullWeatherFromNetwork_emitHttpError(): Unit = runBlocking {

        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = getFullWeatherByCityName.execute(CITY_NAME, true).toList()

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
}