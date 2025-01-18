// domain/src/main/java/com/williamfq/domain/model/ChatMessage.kt
package com.williamfq.domain.model

data class ChatMessage(
    val id: Long,
    val chatId: String,
    val senderId: String,
    val recipientId: String,
    val message: String, // Propiedad necesaria
    val timestamp: Long,
    val isRead: Boolean,
    val isSent: Boolean,
    val isDeleted: Boolean,
    val type: MessageType, // Propiedad necesaria
    val status: MessageStatus
)

