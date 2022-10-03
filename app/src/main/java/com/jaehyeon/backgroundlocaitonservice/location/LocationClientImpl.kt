package com.jaehyeon.backgroundlocaitonservice.location

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.jaehyeon.backgroundlocaitonservice.ext.hasLocationPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

/**
 * Created by Jaehyeon on 2022/10/03.
 */
class LocationClientImpl(
    private val context: Context,
    private val client: FusedLocationProviderClient
): LocationClient {

    override fun getLocationUpdates(interval: Long): Flow<Location> = callbackFlow {
        if (!context.hasLocationPermission()) throw LocationClient.LocationException("권한이 없음.")

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnable && !isNetworkEnable) throw LocationClient.LocationException("GPS가 꺼져있음.")

        val request = LocationRequest.create()
            .setInterval(interval)
            .setFastestInterval(interval)

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                result.locations.lastOrNull()?.let { location ->
                    launch { send(location) }
                }
            }
        }

        client.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )

        awaitClose {
            client.removeLocationUpdates(locationCallback)
        }
    }

}