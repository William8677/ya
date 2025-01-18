// domain/src/main/java/com/williamfq/domain/repository/ChatMessageRepository.kt
package com.williamfq.domain.repository

import com.williamfq.domain.model.ChatMessage

/**
 * Interfaz del repositorio en la capa de dominio.
 * Otras capas (data) implementarán esta interfaz.
 */
interface ChatMessageRepository {
    suspend fun sendMessage(message: ChatMessage)
    suspend fun getMessages(chatId: String): List<ChatMessage>
}
