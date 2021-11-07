package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.fullweather

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.CurrentWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.fullweather.*
import mobile.app.android.myavuzbahceci.weatherforecast.business.util.TAG
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.util.DialogQueue
import mobile.app.android.myavuzbahceci.weatherforecast.framework.util.ConnectivityManager
import javax.inject.Inject


@SuppressLint("MissingPermission")
@HiltViewModel
class FullWeatherViewModel
@Inject
constructor(
    private val getCurrentLocation: GetCurrentLocation,
    private val getCurrentWeatherByCityName: GetCurrentWeatherByCityName,
    private val getCurrentWeatherByCoords: GetCurrentWeatherByCoords,
    private val connectivityManager: ConnectivityManager,
    private val getFullWeatherByCityName: GetFullWeatherByCityName,
    private val getFullWeatherWithCoords: GetFullWeatherWithCoords
) : ViewModel() {

    val dailyWeatherList: MutableState<List<FullWeather.Daily>> = mutableStateOf(ArrayList())

    val currentWeather: MutableState<CurrentWeather?> = mutableStateOf(null)

    val currentCity: MutableState<FullWeather.City?> = mutableStateOf(null)

    val query = mutableStateOf("")

    val selectedCity: MutableState<City?> = mutableStateOf(null)

    @SuppressLint("MissingPermission")
    val coords: MutableState<Location?> = mutableStateOf(null)

    var weatherListScrollPosition = 0

    val loading = mutableStateOf(false)

    val dialogQueue = DialogQueue()

    val currentFetched: MutableState<Boolean> = mutableStateOf(false)

    init {
        runBlocking {
            println("Trigger Get Coords : ${coords.value}")
            onTriggerEvent(FullWeatherStateEvent.GetCoords())
        }
        println("Trigger Get Coords Ran: ${coords.value}")
        coords.value?.let {
            onTriggerEvent(
                FullWeatherStateEvent.GetFullWeatherStateByCoords(
                    it.longitude,
                    it.latitude
                )
            )
            onTriggerEvent(
                FullWeatherStateEvent.GetCurrentWeatherStateByCoords(
                    it.longitude,
                    it.latitude
                )
            )
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onTriggerEvent(event: FullWeatherStateEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is FullWeatherStateEvent.GetCoords -> {
                        attemptGetCoordinates()
                    }
                    is FullWeatherStateEvent.GetCurrentWeatherStateByCityName -> {
                        attemptGetCurrentWeatherByCityName()
                    }
                    is FullWeatherStateEvent.GetCurrentWeatherStateByCoords -> {
                        attemptGetCurrentWeatherByCoords()
                    }
                    is FullWeatherStateEvent.GetFullWeatherStateByCityName -> {
                        attemptGetFullWeatherByCityName()
                    }
                    is FullWeatherStateEvent.GetFullWeatherStateByCoords -> {
                        attemptGetFullWeatherByCoords()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
            } finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private fun attemptGetFullWeatherByCoords() {
        resetSearchState()
        coords.value?.longitude?.let { longitude ->
            coords.value?.latitude?.let { latitude ->
                getFullWeatherWithCoords.execute(
                    longitude,
                    latitude,
                    connectivityManager.isNetworkAvailable.value
                ).onEach { dataState ->
                    loading.value = dataState.loading

                    dataState.data?.let { fullWeather ->
                        dailyWeatherList.value = fullWeather.daily
                        currentCity.value = fullWeather.city
                    }
                    dataState.error?.let { error ->
                        Log.e(TAG, "newSearch: ${error}")
                        dialogQueue.addDialogMessage("An Error Occurred", error)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun attemptGetFullWeatherByCityName() {
        resetSearchState()

        getFullWeatherByCityName.execute(
            query.value.capitalize(Locale.current),
            connectivityManager.isNetworkAvailable.value
        )
            .onEach { dataState ->
                loading.value = dataState.loading
                dataState.data?.let { fullWeather ->
                    dailyWeatherList.value = fullWeather.daily
                    currentCity.value = fullWeather.city
                }
                dataState.error?.let { error ->
                    Log.e(TAG, "newSearch: ${error}")
                    dialogQueue.addDialogMessage("An Error Occurred", error)
                }
            }.launchIn(viewModelScope)
    }

    private fun attemptGetCurrentWeatherByCoords() {
        resetSearchState()
        coords.value?.longitude?.let { longitude ->
            coords.value?.latitude?.let { latitude ->
                getCurrentWeatherByCoords.execute(
                    longitude,
                    latitude,
                    connectivityManager.isNetworkAvailable.value
                ).onEach { dataState ->
                    loading.value = dataState.loading

                    dataState.data?.let { cW ->
                        currentWeather.value = cW
                    }
                    dataState.error?.let { error ->
                        Log.e(TAG, "newSearch: ${error}")
                        dialogQueue.addDialogMessage("An Error Occurred", error)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun attemptGetCurrentWeatherByCityName() {
        resetSearchState()
        getCurrentWeatherByCityName.execute(
            query.value.capitalize(Locale.current),
            connectivityManager.isNetworkAvailable.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { cW ->
                currentWeather.value = cW
            }
            dataState.error?.let { error ->
                Log.e(TAG, "newSearch: ${error}")
                dialogQueue.addDialogMessage("An Error Occurred", error)
            }
        }.launchIn(viewModelScope)
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun attemptGetCoordinates() {
        getCurrentLocation.execute().onEach { location ->
            coords.value = location
        }.launchIn(viewModelScope)
    }

    private fun resetSearchState() {
        dailyWeatherList.value = listOf()
        onChangeWeatherScrollPosition(0)
        if (selectedCity.value?.value != query.value) clearSelectedCity()
    }

    fun onChangeWeatherScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    private fun setSelectedCity(city: City?) {
        selectedCity.value = city
    }

    private fun setQuery(query: String) {
        this.query.value = query
    }

    private fun clearSelectedCity() {
        setSelectedCity(null)
        selectedCity.value = null
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    fun onSelectedCityChanged(cityName: String) {
        val newCity = getCity(cityName)
        setSelectedCity(newCity)
        onQueryChanged(cityName)
    }

    private fun setListScrollPosition(position: Int) {
        weatherListScrollPosition = position
    }

    fun onToggleLocation() {
        currentCity.value = null
        resetSearchState()
        currentFetched.value = false
        onTriggerEvent(FullWeatherStateEvent.GetCoords())
    }
}