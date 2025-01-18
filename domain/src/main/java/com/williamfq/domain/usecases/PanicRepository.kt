// En com.williamfq.domain.usecases.PanicRepository.kt
package com.williamfq.domain.usecases

import com.williamfq.domain.models.PanicAlert
import com.williamfq.domain.models.PanicAlertStatus

interface PanicRepository {
    suspend fun sendPanicAlert(panicAlert: PanicAlert): Result<Unit>
    suspend fun getPanicAlerts(userId: String): Result<List<PanicAlert>>
    suspend fun updatePanicAlertStatus(alertId: Long, status: PanicAlertStatus): Result<Unit>
}