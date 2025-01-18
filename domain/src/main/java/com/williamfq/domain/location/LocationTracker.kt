package com.williamfq.domain.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz que define las funciones relacionadas con el rastreo de ubicación en el dominio del proyecto Xhat.
 */
interface LocationTracker {

    /**
     * Verifica si los permisos de ubicación están otorgados.
     *
     * @return `true` si los permisos están otorgados, de lo contrario `false`.
     */
    fun isLocationPermissionGranted(): Boolean

    /**
     * Obtiene la ubicación actual del usuario.
     *
     * @return Un [Flow] que emite la ubicación actual como [Location] o `null` si no está disponible.
     */
    fun getCurrentLocation(): Flow<Location?>

    /**
     * Inicia las actualizaciones de ubicación en tiempo real.
     *
     * @return Un [Flow] que emite actualizaciones periódicas de la ubicación como [Location].
     */
    fun startLocationUpdates(): Flow<Location>

    /**
     * Detiene las actualizaciones de ubicación en tiempo real.
     */
    fun stopLocationUpdates()
}
