package com.williamfq.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.williamfq.domain.location.LocationTracker
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import javax.inject.Inject

class LocationTrackerImpl @Inject constructor(
    private val context: Context
) : LocationTracker {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val _locationUpdates = MutableLiveData<Location>()
    val locationUpdates: LiveData<Location> get() = _locationUpdates

    override fun isLocationPermissionGranted(): Boolean {
        return EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Flow<Location?> = callbackFlow {
        if (!isLocationPermissionGranted()) {
            trySend(null)
            close()
            return@callbackFlow
        }

        val cancellationTokenSource = CancellationTokenSource()
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            trySend(location).isSuccess
        }.addOnFailureListener { exception ->
            Timber.tag("LocationTracker").e("Error fetching location: ${exception.message}")
            trySend(null).isFailure
        }

        awaitClose { cancellationTokenSource.cancel() }
    }

    @SuppressLint("MissingPermission")
    override fun startLocationUpdates(): Flow<Location> = callbackFlow {
        if (!isLocationPermissionGranted()) {
            close()
            return@callbackFlow
        }

        val locationRequest = LocationRequest.create().apply {
            interval = 5000L
            fastestInterval = 2000L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    trySend(location).isSuccess
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            .addOnFailureListener { exception ->
                Timber.tag("LocationTracker")
                    .e("Failed to start location updates: ${exception.message}")
                close(exception)
            }

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }.catch { exception ->
        Timber.tag("LocationTracker").e("Error during location updates: ${exception.message}")
    }.onCompletion {
        Timber.tag("LocationTracker").d("Location updates completed")
    }

    override fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates { }
    }
}
