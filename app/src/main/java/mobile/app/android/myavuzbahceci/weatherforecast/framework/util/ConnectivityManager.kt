package mobile.app.android.myavuzbahceci.weatherforecast.framework.util

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityManager
@Inject
constructor(
    application: Application,
) {

    // observe this in ui
    val isNetworkAvailable = mutableStateOf(false)

    private val liveConnectionData = ConnectionLiveData(application)

    fun registerConnection(lifecycleOwner: LifecycleOwner){
        liveConnectionData.observe(lifecycleOwner, { isConnected ->
            isConnected?.let { isNetworkAvailable.value = it }
        })
    }

    fun unregisterConnection(lifecycleOwner: LifecycleOwner){
        liveConnectionData.removeObservers(lifecycleOwner)
    }
}