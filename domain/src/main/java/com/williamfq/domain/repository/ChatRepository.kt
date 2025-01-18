// domain/src/main/java/com/williamfq/domain/repository/ChatRepository.kt
package com.williamfq.domain.repository

import com.williamfq.domain.model.ChatMessage
import com.williamfq.domain.model.MessageStatus
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz del repositorio para gestionar los mensajes del chat
 * Created by William8677 on 2025-01-04 02:56:19
 */
interface ChatRepository {
    fun getOfflineMessages(): Flow<List<ChatMessage>>
    fun getMessagesByChatId(chatId: String): Flow<List<ChatMessage>>
    suspend fun saveMessage(message: ChatMessage)
    suspend fun deleteMessage(messageId: Long)
    suspend fun updateMessageStatus(messageId: Long, status: MessageStatus)

    suspend fun sendMessage(chatMessage: ChatMessage)
}
