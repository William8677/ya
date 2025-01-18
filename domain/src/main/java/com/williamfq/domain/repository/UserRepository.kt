// File: domain/src/main/java/com/williamfq/domain/repository/UserRepository.kt
package com.williamfq.domain.repository

/**
 * Interfaz que define las operaciones relacionadas con los datos del usuario
 *
 * @author William8677
 * @since 2025-01-04 03:05:51
 */
interface UserRepository {
    /**
     * Obtiene el ID del usuario actualmente autenticado
     * @return String con el ID del usuario
     */
    fun getCurrentUserId(): String

    /**
     * Guarda el ID del usuario actual
     * @param userId ID del usuario a guardar
     */
    suspend fun saveUserId(userId: String)

    /**
     * Verifica si hay un usuario autenticado
     * @return true si hay un usuario autenticado, false en caso contrario
     */
    fun isUserLoggedIn(): Boolean

    /**
     * Elimina los datos del usuario actual (logout)
     */
    suspend fun clearUserData()
}
