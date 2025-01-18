// domain/src/main/java/com/williamfq/domain/models/PanicAlert.kt
package com.williamfq.domain.models

/**
 * Modelo de una alerta de pánico.
 */
data class PanicAlert(
    val id: Long = 0,
    val message: String,
    val userId: String,
    val location: Location?, // Información geográfica
    val timestamp: Long = System.currentTimeMillis(),
    val status: PanicAlertStatus = PanicAlertStatus.SENDING
)
