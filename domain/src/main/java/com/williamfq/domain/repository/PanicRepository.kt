// domain/src/main/java/com/williamfq/domain/repository/PanicRepository.kt
package com.williamfq.domain.repository

import com.williamfq.domain.models.Result
import com.williamfq.domain.models.PanicAlert
import com.williamfq.domain.models.PanicAlertStatus

/**
 * Repositorio para manejar las alertas de pánico.
 * Xhat no solo facilita la comunicación, sino que también te brinda seguridad en situaciones críticas.
 */
interface PanicRepository {
    /**
     * Envía una alerta de pánico con un mensaje y detalles adicionales.
     *
     * @param panicAlert Los detalles de la alerta de pánico.
     * @return Un [Result] indicando éxito o fallo.
     */
    suspend fun sendPanicAlert(
        panicAlert: PanicAlert
    ): Result<Unit>

    /**
     * Recupera el historial de alertas de pánico enviadas por un usuario.
     *
     * @param userId ID del usuario.
     * @return Un [Result] con una lista de [PanicAlert].
     */
    suspend fun getPanicAlerts(userId: String): Result<List<PanicAlert>>

    /**
     * Actualiza el estado de una alerta de pánico.
     *
     * @param alertId ID de la alerta a actualizar.
     * @param status Nuevo estado de la alerta.
     * @return Un [Result] indicando éxito o fallo.
     */
    suspend fun updatePanicAlertStatus(
        alertId: Long,
        status: PanicAlertStatus
    ): Result<Unit>
}
