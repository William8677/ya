package com.xhat.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LocationTracker(private val context: Context) {

    // Obtener la instancia de FusedLocationProviderClient correctamente
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val locationFlow = MutableStateFlow<Location?>(null)

    private val locationRequest = LocationRequest.create().apply {
        interval = 15000 // 15 segundos
        fastestInterval = 10000 // 10 segundos
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let {
                locationFlow.value = it
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startTracking() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun stopTracking() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun getLocationUpdates(): StateFlow<Location?> = locationFlow
}
