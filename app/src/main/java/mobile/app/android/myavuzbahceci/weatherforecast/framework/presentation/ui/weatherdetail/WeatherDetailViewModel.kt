package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.weatherdetail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mobile.app.android.myavuzbahceci.weatherforecast.business.domain.model.FullWeather
import mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.weatherdetail.GetRelatedFullWeatherItem
import mobile.app.android.myavuzbahceci.weatherforecast.business.util.TAG
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.util.DialogQueue
import mobile.app.android.myavuzbahceci.weatherforecast.framework.util.ConnectivityManager
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel
@Inject
constructor(
    private val getRelatedFullWeatherItem: GetRelatedFullWeatherItem,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {


    val selectedDaily: MutableState<FullWeather.Daily?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    val dialogQueue = DialogQueue()

    fun onTriggerEvent(event: WeatherDetailStateEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is WeatherDetailStateEvent.GetRelatedFullWeatherItem -> {
                        attemptGetRelatedFullWeatherItem(
                            event.lon,
                            event.lat,
                            event.cityName,
                            event.dt
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
            } finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private fun attemptGetRelatedFullWeatherItem(
        lon: Double?,
        lat: Double?,
        cityName: String?,
        dt: Long
    ) {
        getRelatedFullWeatherItem.execute(
            cityName,
            lon,
            lat,
            dt,
            connectivityManager.isNetworkAvailable.value
        )
            .onEach { dataState ->
                loading.value = dataState.loading
                dataState.data?.let { daily ->
                    selectedDaily.value = daily
                }
                dataState.error?.let { error ->
                    Log.e(TAG, "newSearch: ${error}")
                    dialogQueue.addDialogMessage("An Error Occurred", error)
                }
            }.launchIn(viewModelScope)
    }


}