package mobile.app.android.myavuzbahceci.weatherforecast.business.interactors.fullweather

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow

class GetCurrentLocation(
    val context: Context
) {
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun execute() = getLocation()

    @ExperimentalCoroutinesApi
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun getLocation() = channelFlow<Location> {
        val client = LocationServices.getFusedLocationProviderClient(context)
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                trySend(result.lastLocation)
            }
        }
        val request = LocationRequest.create()
            .setInterval(10_000)
            .setFastestInterval(5_000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setSmallestDisplacement(170f)
        client.requestLocationUpdates(request, callback, Looper.getMainLooper())
        awaitClose {
            client.removeLocationUpdates(callback)
        }
    }
}