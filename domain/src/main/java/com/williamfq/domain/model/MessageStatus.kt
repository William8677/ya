// domain/src/main/java/com/williamfq/domain/model/MessageStatus.kt
package com.williamfq.domain.model

/**
 * Enum para el estado del mensaje en la capa de dominio.
 */
enum class MessageStatus {
    SENDING,
    SENT,
    FAILED,
    DELIVERED,
    READ
}
