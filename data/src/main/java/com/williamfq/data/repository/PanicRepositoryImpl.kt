// data/src/main/java/com/williamfq/data/repository/PanicRepositoryImpl.kt
package com.williamfq.data.repository

import com.williamfq.domain.models.*
import com.williamfq.domain.repository.PanicRepository
import com.williamfq.data.dao.PanicDao
import com.williamfq.data.mapper.toDomain
import com.williamfq.data.mapper.toEntity
import com.williamfq.data.services.AlertService
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Implementación del repositorio de alertas de pánico.
 */
@Singleton
class PanicRepositoryImpl @Inject constructor(
    private val alertService: AlertService, // Servicio para enviar alertas
    private val panicDao: PanicDao // DAO para persistencia local
) : PanicRepository {

    override suspend fun sendPanicAlert(
        panicAlert: PanicAlert
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                // Persistir la alerta en la base de datos local
                panicDao.insertPanicAlert(panicAlert.toEntity())

                // Enviar la alerta a través del servicio
                alertService.sendAlert(
                    message = panicAlert.message,
                    userId = panicAlert.userId,
                    location = panicAlert.location
                )

                // Actualizar el estado de la alerta
                panicDao.updatePanicAlertStatus(panicAlert.id, PanicAlertStatus.SENT.toEntity())

                Result.Success(Unit)
            } catch (e: Exception) {
                Timber.e(e, "Error sending panic alert for userId=${panicAlert.userId}")
                // Actualizar el estado de la alerta como fallida
                panicDao.updatePanicAlertStatus(panicAlert.id, PanicAlertStatus.FAILED.toEntity())
                Result.Error(e)
            }
        }
    }

    override suspend fun getPanicAlerts(userId: String): Result<List<PanicAlert>> {
        return withContext(Dispatchers.IO) {
            try {
                val alerts = panicDao.getPanicAlertsByUserId(userId).map { it.toDomain() }
                Result.Success(alerts)
            } catch (e: Exception) {
                Timber.e(e, "Error retrieving panic alerts for userId=$userId")
                Result.Error(e)
            }
        }
    }

    override suspend fun updatePanicAlertStatus(
        alertId: Long,
        status: PanicAlertStatus
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                panicDao.updatePanicAlertStatus(alertId, status.toEntity())
                Result.Success(Unit)
            } catch (e: Exception) {
                Timber.e(e, "Error updating panic alert status for alertId=$alertId")
                Result.Error(e)
            }
        }
    }
}
